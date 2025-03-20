package org.example.automanager.services;

import lombok.RequiredArgsConstructor;
import org.example.automanager.model.Place;
import org.example.automanager.model.Review;
import org.example.automanager.repository.CarRepository;
import org.example.automanager.repository.PlaceRepository;
import org.example.automanager.repository.ReviewRepository;
import org.example.automanager.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;

    public Set<Review> getReviews(UUID serviceId) {
        Place place = placeRepository.findById(serviceId).get();

        return null;
    }
}
