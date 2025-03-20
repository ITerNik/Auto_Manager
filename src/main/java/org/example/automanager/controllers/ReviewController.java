package org.example.automanager.controllers;

import lombok.RequiredArgsConstructor;
import org.example.automanager.model.Review;
import org.example.automanager.services.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{id}")
    public List<Review> getServiceReviews(@PathVariable UUID id) {
        return reviewService.getReviews(id).stream().toList();
    }
}
