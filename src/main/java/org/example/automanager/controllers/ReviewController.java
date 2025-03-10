package org.example.automanager.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.automanager.dto.review.ReviewCreateRequest;
import org.example.automanager.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/places/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{id}")
    public ResponseEntity<?> createReview(
            @PathVariable("id") @Valid UUID placeId,
            @RequestBody @Valid ReviewCreateRequest request,
            @RequestHeader("Authorization") String jwt
    ) {
        reviewService.createReview(placeId, request, jwt);
        return ResponseEntity.ok().body("Success!");
    }

    @ExceptionHandler
    public ResponseEntity<String> handler(Exception e) {
        return ResponseEntity.badRequest().body("Error -> " + e.getMessage());
    }
}
