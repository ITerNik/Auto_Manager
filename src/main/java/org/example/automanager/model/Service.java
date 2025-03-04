package org.example.automanager.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "service")
@Data
public class Service {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "service_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place; // Assuming you have a Place entity

    @ManyToMany(mappedBy = "services")
    private Set<Client> clients;

    @ManyToMany(mappedBy = "favoriteServices")
    private Set<Client> likedClients;
}
