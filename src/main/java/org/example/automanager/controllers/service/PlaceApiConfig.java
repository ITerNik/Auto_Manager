package org.example.automanager.controllers.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceApiConfig {
    @Value("${placeapi.key}")
    private String key;
}
