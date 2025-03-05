package org.example.automanager.services;

import lombok.RequiredArgsConstructor;
import org.example.automanager.repository.ServiceRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceService {
    private final ServiceRepository serviceRepository;
}
