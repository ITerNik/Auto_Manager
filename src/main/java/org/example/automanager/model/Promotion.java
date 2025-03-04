package org.example.automanager.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "promotion")
@Data
public class Promotion {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_by", nullable = false)
    private Integer createdBy; // Assuming it refers to Client.id which is UUID

    @Column(name = "is_archived", nullable = false)
    private Boolean isArchived = false;

    @Column(name = "title", length = 32)
    private String title;
}

