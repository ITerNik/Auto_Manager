package org.example.automanager.repository;

import org.example.automanager.model.Inaccuracy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InaccuracyRepository extends JpaRepository<Inaccuracy, UUID> {
    
}
