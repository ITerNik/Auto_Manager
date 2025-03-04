package org.example.automanager.security.auth;

import lombok.RequiredArgsConstructor;
import org.example.automanager.model.Client;
import org.example.automanager.model.Role;
import org.example.automanager.repository.ClientRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public Client save(Client client) {
        return clientRepository.save(client);
    }


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public Client create(Client client) {
        if (clientRepository.existsByEmail(client.getUsername())) {
            // Заменить на свои исключения
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        return save(client);
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
}