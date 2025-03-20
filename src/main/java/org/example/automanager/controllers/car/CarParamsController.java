package org.example.automanager.controllers.car;

import lombok.RequiredArgsConstructor;
import org.example.automanager.dto.auth.car.CarParams;
import org.example.automanager.dto.auth.car.CarParamsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/params")
public class CarParamsController {
    private final RestClient client;

    private List<CarParams> getAll() {
        return client.get()
                .uri("https://myfakeapi.com/api/cars")
                .retrieve()
                .body(CarParamsResponse.class)
                .getCars();
    }

    @GetMapping("/makes")
    public ResponseEntity<Set<String>> getMakes() {
       return ResponseEntity.ok().body(
               this.getAll()
               .stream()
               .map((CarParams::getCar)).collect(Collectors.toSet()));
    }

    @GetMapping("/models")
    public ResponseEntity<Set<String>> getModels(
            @RequestParam(value = "make", required = false) String make
    ) {
        List<CarParams> cars = this.getAll();
        if (make != null) {
            cars = cars.stream()
                    .filter((car) -> car.getCar().equals(make))
                    .toList();
        }

        return ResponseEntity.ok().body(cars.stream()
                .map(CarParams::getCarModel).collect(Collectors.toSet()));

    }

    @ExceptionHandler
    public ResponseEntity<String> handler(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
