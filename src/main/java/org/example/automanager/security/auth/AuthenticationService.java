package org.example.automanager.security.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.automanager.dto.auth.ClientInfoResponse;
import org.example.automanager.dto.auth.JwtAuthenticationResponse;
import org.example.automanager.dto.auth.SignInRequest;
import org.example.automanager.dto.auth.SignUpRequest;
import org.example.automanager.dto.profile.EditProfileRequest;
import org.example.automanager.model.Client;
import org.example.automanager.model.Role;
import org.example.automanager.security.jwt.JwtService;
import org.example.automanager.services.ClientService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final ClientService clientService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        var user = Client.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        clientService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        var user = clientService
                .userDetailsService()
                .loadUserByUsername(request.getEmail());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    @Transactional
    public void updateClientInfo(UUID uuid, EditProfileRequest request, String token) {
        token = token.substring(7);
        String email = jwtService.extractUserName(token);
        if (!clientService.getByUsername(email).getId().equals(uuid))
            throw new IllegalArgumentException("Something went wrong!");

        String newName = request.getName();
        LocalDate newBirthday = request.getBirthday();
        String newSurname = request.getSurname();
        String newPassword = request.getNewPassword();
        String oldPassword = request.getPassword();
        Client client = clientService.getById(uuid);

        if (newPassword != null && oldPassword != null) {
            if (!passwordEncoder.matches(oldPassword, client.getPassword()))
                throw new IllegalArgumentException("Password is incorrect!");

            client.setPassword(passwordEncoder.encode(newPassword));
        }

        if (newSurname != null) client.setSurname(newSurname);
        if (newName != null) client.setName(newName);
        if (newBirthday != null) client.setBirthday(newBirthday);

        clientService.save(client);
    }

    public ClientInfoResponse getClientInfoByJwtToken(String token) {
        String email = jwtService.extractUserName(token);
        Client client = clientService.getByUsername(email);

        return ClientInfoResponse.builder()
                .id(client.getId())
                .email(email)
                .name(client.getName())
                .surname(client.getSurname())
                .birthday(client.getBirthday())
                .build();
    }
}