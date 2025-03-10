package org.example.automanager.services;

import lombok.RequiredArgsConstructor;
import org.example.automanager.model.Address;
import org.example.automanager.repository.AddressRepository;
import org.example.automanager.repository.CarRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address save(Address address) {
        if (!addressRepository.isAddressExists(
                address.getCity(),
                address.getStreet(),
                address.getHouse()))
            return addressRepository.save(address);
        return addressRepository.getByStreetHouseAndCity(
                address.getCity(),
                address.getStreet(),
                address.getHouse()
        );
    }
}
