package org.example.automanager.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.automanager.dto.auth.ClientInfoResponse;
import org.example.automanager.model.Client;
import org.example.automanager.model.Role;
import org.example.automanager.repository.ClientRepository;
import org.example.automanager.security.jwt.JwtService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final JwtService jwtService;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    /**
     * Удаление пользователя
     */
    @Transactional
    public void deleteClient(UUID uuid) {
        clientRepository.deleteById(uuid);
    }


    /**
     * Создание пользователя
     */
    public void create(Client client) {
        if (clientRepository.existsByEmail(client.getUsername())) {
            // Заменить на свои исключения
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        save(client);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public Client getByUsername(String email) {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    public Client getById(UUID uuid) {
        return clientRepository.findById(uuid)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    public void removeClientCarById(UUID carId) {
        getCurrentUser().removeCarById(carId);
    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public Client getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    public UUID getCurrentUserId() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username).getId();
    }

    /**
     * Выдача прав администратора текущему пользователю
     * <p>
     * Нужен для демонстрации
     */
    @Deprecated
    public void getAdmin() {
        var client = getCurrentUser();
        client.setRole(Role.ADMIN);
        save(client);
    }

    public boolean checkValidClient(UUID uuid, String token) {
        token = token.substring(7);
        String email = jwtService.extractUserName(token);
        return getByUsername(email).getId().equals(uuid);
    }

    public void deleteClient(UUID uuid, String token) {
        if (checkValidClient(uuid, token))
            deleteClient(uuid);
        throw new IllegalArgumentException("Something went wrong!");
    }

    public Client getClientInfoById(UUID uuid, String token) {
        if (checkValidClient(uuid, token))
            return getById(uuid);
        throw new IllegalArgumentException("Something went wrong!");
    }
}