package org.example.automanager.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "recommendation")
@Data
public class Recommendation {
    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "status", nullable = false, length = 32)
    private String status;

    @Column(name = "comment", nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
