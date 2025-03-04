package org.example.automanager.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.automanager.dto.auth.JwtAuthenticationResponse;
import org.example.automanager.dto.auth.SignInRequest;
import org.example.automanager.dto.auth.SignUpRequest;
import org.example.automanager.security.auth.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }
}