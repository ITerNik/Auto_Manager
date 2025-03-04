package org.example.automanager.services;

import lombok.RequiredArgsConstructor;
import org.example.automanager.repository.CarRepository;
import org.example.automanager.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
}
