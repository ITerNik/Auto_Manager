package org.example.automanager.services;

import lombok.RequiredArgsConstructor;
import org.example.automanager.repository.CarRepository;
import org.example.automanager.repository.PlaceRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
}
