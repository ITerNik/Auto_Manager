package org.example.automanager.controllers;

import lombok.RequiredArgsConstructor;
import org.example.automanager.model.Client;
import org.example.automanager.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final ClientService clientService;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable("id") UUID uuid) {
        clientService.deleteClient(uuid);
        return ResponseEntity.ok().body("Success!");
        //else return ResponseEntity.badRequest().body("Something went wrong!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getClientInfo(@PathVariable("id") UUID uuid) {
        Client client = clientService.getById(uuid);

        return ResponseEntity.ok().body(client.toString());
    }

    @ExceptionHandler
    public ResponseEntity<String> handler(Exception e) {
        return ResponseEntity.badRequest().body("Error -> " + e.getMessage());
    }
}
