package org.example.automanager.services;

import lombok.RequiredArgsConstructor;
import org.example.automanager.repository.CarRepository;
import org.example.automanager.repository.PromotionClientRepository;
import org.example.automanager.repository.PromotionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionClientService {
    private final PromotionClientRepository promotionClientRepository;
}
