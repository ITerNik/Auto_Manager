package org.example.automanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "review")
@Data
@NoArgsConstructor
public class Review {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "status", length = 32, nullable = false)
    private String status;

    @Column(name = "comment", nullable = false, columnDefinition = "TEXT")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "updated_at")
    private Instant updatedAt;
}

