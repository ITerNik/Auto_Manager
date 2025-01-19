package org.example.automanager.controllers;

import lombok.Data;
import org.example.automanager.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
public class CarController {
    @Autowired
    private CarRepository carRepository;

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(carRepository.getAll());
    }
}
