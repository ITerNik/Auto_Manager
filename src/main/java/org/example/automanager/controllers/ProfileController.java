package org.example.automanager.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.automanager.dto.auth.ClientInfoResponse;
import org.example.automanager.dto.profile.EditProfileRequest;
import org.example.automanager.model.Client;
import org.example.automanager.security.auth.AuthenticationService;
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
    private final AuthenticationService authenticationService;

    @GetMapping("/")
    public ResponseEntity<ClientInfoResponse> getClientInfo(@RequestHeader("Authorization") String jwt) {
        String token = jwt.substring(7);
        ClientInfoResponse info = authenticationService.getClientInfoByJwtToken(token);
        return ResponseEntity.ok().body(info);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(
            @PathVariable("id") UUID uuid,
            @RequestHeader("Authorization") String jwt
    ) {
        clientService.deleteClient(uuid, jwt);
        return ResponseEntity.ok().body("Success!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getClientInfo(
            @PathVariable("id") UUID uuid,
            @RequestHeader("Authorization") String jwt
    ) {
        Client client = clientService.getClientInfoById(uuid, jwt);
        return ResponseEntity.ok().body(client.toString());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> editProfile(
            @PathVariable("id") UUID uuid,
            @RequestBody @Valid EditProfileRequest request,
            @RequestHeader("Authorization") String jwt
    ) {
        authenticationService.updateClientInfo(uuid, request, jwt);
        return ResponseEntity.ok().body("Success!");
    }

    @ExceptionHandler
    public ResponseEntity<String> handler(Exception e) {
        return ResponseEntity.badRequest().body("Error -> " + e.getMessage());
    }
}
