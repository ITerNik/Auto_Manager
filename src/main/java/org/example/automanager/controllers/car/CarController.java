package org.example.automanager.controllers.car;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.automanager.dto.car.CarInfoRequest;
import org.example.automanager.model.Car;
import org.example.automanager.services.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping("/")
    public ResponseEntity<List<Car>> getUserCars() {
        List<Car> userCars = carService.getUserCars();
        return ResponseEntity.ok(userCars);
    }

    @PostMapping("/")
    public ResponseEntity<?> addClientCar(
            @RequestBody @Valid CarInfoRequest request,
            @RequestHeader("Authorization") String jwt
    ) {
        carService.addCar(request, jwt);
        return ResponseEntity.ok().body("Success!");
    }

    @DeleteMapping("/{car_id}")
    public ResponseEntity<String> deleteClientCar(
            @PathVariable("car_id") UUID carId,
            @RequestHeader("Authorization") String jwt
    ) {
        carService.deleteCar(carId, jwt);
        return ResponseEntity.ok().body("Success!");
    }

    @PatchMapping("/{car_id}")
    public ResponseEntity<String> editClientCar(
            @PathVariable("car_id") UUID carId,
            @RequestBody @Valid CarInfoRequest request,
            @RequestHeader("Authorization") String jwt
    ) {
        carService.editCarById(carId, request, jwt);
        return ResponseEntity.ok().body("Success!");
    }

    @ExceptionHandler
    public ResponseEntity<String> handler(Exception e) {
        return ResponseEntity.badRequest().body("Error -> " + e.getMessage());
    }
}
