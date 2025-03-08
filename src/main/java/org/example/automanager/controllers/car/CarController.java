package org.example.automanager.controllers.car;

import lombok.RequiredArgsConstructor;
import org.example.automanager.model.Car;
import org.example.automanager.services.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping()
    public ResponseEntity<List<Car>> getUserCars() {
        List<Car> userCars = carService.getUserCars();
        return ResponseEntity.ok(userCars);
    }
}
