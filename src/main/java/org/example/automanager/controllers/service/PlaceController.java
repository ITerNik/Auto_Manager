package org.example.automanager.controllers.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.automanager.services.PlaceService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping("/range")
    public ResponseEntity<?> getAllPlacesInRange(
            @RequestParam("r") @Valid int r,
            @RequestParam("latitude") @Valid double latitude,
            @RequestParam("longitude") @Valid double longitude,
            @RequestParam(value = "types", defaultValue = "Автосервис") @Valid String[] q
    ) {
        return ResponseEntity.ok().body(placeService.getAllPlacesInRange(q, longitude, latitude, r));
    }

    @GetMapping("/name")
    public HttpEntity<?> getAllPlacesByName(
            @RequestParam("q") @Valid String name,
            @RequestParam("latitude") @Valid double latitude,
            @RequestParam("longitude") @Valid double longitude
    ) {
        return ResponseEntity.ok().body(placeService.findPlacesByNameContaining(name, longitude, latitude));
    }

    @ExceptionHandler
    public ResponseEntity<String> handler(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
