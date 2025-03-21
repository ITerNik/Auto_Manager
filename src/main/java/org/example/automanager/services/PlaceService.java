package org.example.automanager.services;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.example.automanager.controllers.service.DistanceCalculator;
import org.example.automanager.controllers.service.PlaceApiConfig;
import org.example.automanager.dto.service.Items;
import org.example.automanager.dto.service.Result;
import org.example.automanager.model.Place;
import org.example.automanager.model.PlaceType;
import org.example.automanager.repository.PlaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final AddressService addressService;
    private final PlaceApiConfig placeApiConfig;
    private final RestClient client;

    public Place save(Place place) {
        if (!placeRepository.isPlaceExists(place.getName(), place.getType().toString(), place.getAddress().getId()))
            return placeRepository.save(place);
        return placeRepository.getByNameTypeAndAddressId(
                place.getName(),
                place.getType().toString(),
                place.getAddress().getId()
        );
    }

    public List<Place> getAllPlacesInRange(String[] query, double longitude, double latitude, int r) {
        List<Items> items = new ArrayList<>();
        List<Place> result = new ArrayList<>();
        for (String type : query) {
            items = Lists.newArrayList(
                    Iterables.concat(
                            items,
                            getPlaceInRangeByType(type, longitude, latitude, r)
                    ));
            result = Lists.newArrayList(
                    Iterables.concat(
                            items.stream().map(i -> {
                                var address = i.parseAddress();
                                if (i.getFullName() == null)
                                    address.setCity(getCityByCoordinates(longitude, latitude));

                                address = addressService.save(address);
                                return save(i.parsePlace(address, PlaceType.fromStringStrict(type)));
                            }).toList()
                    ));
        }
        return result;
    }

    private String getCityByCoordinates(double longitude, double latitude) {
        String uri = "https://catalog.api.2gis.com/3.0/items/geocode?" +
                "lon=" + longitude +
                "&lat=" + latitude +
                "&fields=items.adm_div,items.address&type=adm_div.city" +
                "&key=" + placeApiConfig.getKey();

        Result result = client.get()
                .uri(uri)
                .retrieve()
                .body(Result.class);

        assert result != null;
        return result.getResult().getServices().get(0).getFullName();
    }

    private List<Items> getPlaceInRangeByType(String type, double longitude, double latitude, int r) {
        String uri = "https://catalog.api.2gis.com/3.0/items?" +
                "q=" + type +
                "&type=branch" +
                "&point=" + longitude + "," + latitude +
                "&radius=" + r +
                "&key=" + placeApiConfig.getKey() +
                "&sort=distance" +
                "&fields=items.point";

        Result result = client.get()
                .uri(uri)
                .retrieve()
                .body(Result.class);

        assert result != null;
        return Objects.nonNull(result.getResult()) ?
                result.getResult().getServices()
                : List.of();
    }

    public List<Place> findPlacesByNameContaining(String name, double longitude, double latitude) {
        List<Place> foundPlaces = placeRepository.findByNameContainingIgnoreCase(name);

        foundPlaces.removeIf(fPlace -> DistanceCalculator.calculateDistance(
                latitude, longitude,
                fPlace.getLatitude(), fPlace.getLongitude()
        ) > 5000);

        List<Place> newPlaces = new ArrayList<>();

        if (foundPlaces.size() < 10) {
            newPlaces = getExtraPlacesByName(name, longitude, latitude);
            for (Place place : newPlaces)
                addressService.save(place.getAddress());
            placeRepository.saveAll(newPlaces);
        }

        return Lists.newArrayList(Iterables.concat(foundPlaces, newPlaces));
    }

    private List<Place> getExtraPlacesByName(String name, double longitude, double latitude) {
        List<Place> newPlaces = new ArrayList<>();
        for (PlaceType placeType : PlaceType.values()) {
            newPlaces = Lists.newArrayList(
                    Iterables.concat(
                            newPlaces,
                            getPlaceInRangeByType(
                                    placeType.toString() + " " + name,
                                    longitude,
                                    latitude,
                                    1000
                            ).stream()
                                    .map(i -> {
                                        var address = i.parseAddress();
                                        if (i.getFullName() == null)
                                            address.setCity(getCityByCoordinates(longitude, latitude));

                                        address = addressService.save(address);
                                        return save(i.parsePlace(address, placeType));
                                    })
                                    .collect(Collectors.toList())
                    ));
        }
        return newPlaces;
    }
}
