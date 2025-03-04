package org.example.automanager.services;

import lombok.RequiredArgsConstructor;
import org.example.automanager.repository.CarRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
}
