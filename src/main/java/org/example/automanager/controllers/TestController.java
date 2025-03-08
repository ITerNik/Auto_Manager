package org.example.automanager.controllers;

import lombok.RequiredArgsConstructor;
import org.example.automanager.dto.test.Req;
import org.example.automanager.services.ClientService;
import org.example.automanager.services.MinioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final MinioService minioService;
    private final ClientService clientService;

    @GetMapping("/test")
    public ResponseEntity<?> signUp() {
        minioService.upload();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/id")
    public ResponseEntity<?> getId(@RequestBody Req req) {
        return ResponseEntity.ok().body(clientService.getByUsername(req.getEmail()).getId());
    }

    @ExceptionHandler
    public ResponseEntity<String> handler(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
