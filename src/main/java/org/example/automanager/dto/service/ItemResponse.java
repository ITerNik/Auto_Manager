package org.example.automanager.dto.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ItemResponse {
    @JsonProperty("items")
    private List<Items> services;
}
