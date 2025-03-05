package org.example.automanager.services;

import lombok.RequiredArgsConstructor;
import org.example.automanager.repository.CarRepository;
import org.example.automanager.repository.ServiceNotificationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceNotificationService {
    private final ServiceNotificationRepository serviceNotificationRepository;
}
