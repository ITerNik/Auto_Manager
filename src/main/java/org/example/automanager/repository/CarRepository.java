package org.example.automanager.repository;

import org.example.automanager.model.Car;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {
    @NotNull
    Optional<Car> findById(@NotNull UUID uuid);
}
