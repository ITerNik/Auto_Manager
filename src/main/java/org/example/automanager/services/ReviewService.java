package org.example.automanager.services;

import lombok.RequiredArgsConstructor;
import org.example.automanager.controllers.ProfileController;
import org.example.automanager.dto.review.ReviewCreateRequest;
import org.example.automanager.model.Review;
import org.example.automanager.model.ReviewStatus;
import org.example.automanager.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ClientService clientService;
    private final PlaceService placeService;

    public void archiveReview() {

    }

    public void createReview(UUID placeId, ReviewCreateRequest request, String jwt) {
        reviewRepository.save(
                Review.builder()
                        .createdAt(LocalDate.now())
                        .status(ReviewStatus.PENDING)
                        .client(clientService.getClientInfoById(request.getClientId(), jwt))
                        .place(placeService.findById(placeId))
                        .comment(request.getComment())
                        .rating(request.getRating())
                        .updatedAt(LocalDate.now())
                        .build()
        );
    }
}
