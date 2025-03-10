package org.example.automanager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.automanager.dto.service.Items;
import org.example.automanager.model.Address;
import org.example.automanager.model.Place;
import org.example.automanager.model.PlaceType;
import org.example.automanager.model.ServiceModel;
import org.example.automanager.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceService {
    private final ServiceRepository serviceRepository;
    private final AddressService addressService;
    private final PlaceService placeService;

    public void save(Items place, String placeType) {
        Address address = place.parseAddress();
        addressService.save(address);

        Place parsedPlace = Place.builder()
                .name(place.getName())
                .address(address)
                .type(PlaceType.fromStringStrict(placeType))
                .addressComment(place.getAddressComment())
                .build();
        placeService.save(parsedPlace);

        ServiceModel serviceModel = ServiceModel.builder()
                //.serviceType(ServiceType.fromStringStrict())
                .place(parsedPlace)
                .createdAt(LocalDate.now())
                .description(place.getAddressComment())
                .build();
        serviceRepository.save(serviceModel);
    }
}
