package org.example.automanager.repository;

import org.example.automanager.model.Client;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findByEmail(String email);
    @NotNull
    Optional<Client> findById(@NotNull UUID id);
    boolean existsByEmail(String email);
}
