package org.example.automanager.controllers.service;

import lombok.RequiredArgsConstructor;
import org.example.automanager.services.ServiceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceParamsController {
    private final ServiceService serviceService;

}
