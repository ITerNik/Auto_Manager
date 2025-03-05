package org.example.automanager.controllers;

import lombok.RequiredArgsConstructor;
import org.example.automanager.services.MinioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final MinioService minioService;

    @GetMapping("/test")
    public ResponseEntity<?> signUp() {
        minioService.upload();
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler
    public ResponseEntity<String> handler(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
