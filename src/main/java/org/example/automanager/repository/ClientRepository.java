package org.example.automanager.repository;

import org.example.automanager.model.Client;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findByEmail(String email);

    @NotNull
    Optional<Client> findById(@NotNull UUID id);

    boolean existsByEmail(String email);

    @Modifying
    @Query("update Client c " +
            "set c.name = ?2, c.surname = ?3, " +
                "c.birthday = ?4, c.password = ?5 " +
            "where c.id = ?1")
    void setClientDataById(UUID uuid, String name, String surname, LocalDateTime birthday, String password);
}
