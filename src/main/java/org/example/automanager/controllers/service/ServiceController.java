package org.example.automanager.controllers.service;

import lombok.RequiredArgsConstructor;
import org.example.automanager.services.ServiceService;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceService serviceService;
}
