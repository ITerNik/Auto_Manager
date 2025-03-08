package org.example.automanager.services;

import lombok.RequiredArgsConstructor;
import org.example.automanager.model.Car;
import org.example.automanager.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final ClientService clientService;

    public List<Car> getUserCars() {
        Client client = clientService.getCurrentUser();

        return client.getCars().stream().toList();
    }
}
