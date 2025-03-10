package org.example.automanager.dto.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.UUID;

@Data
public class ReviewCreateRequest {
    @JsonProperty("client_id")
    private UUID clientId;
    private String comment;

    @Max(value = 5, message = "Максимальный возможный рейтинг 5")
    @Min(value = 1, message = "Минимальный возможный рейтинг 1")
    private Integer rating;
}
