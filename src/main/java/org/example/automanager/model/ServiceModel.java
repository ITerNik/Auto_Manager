package org.example.automanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "service")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceModel {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "service_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place; // Assuming you have a Place entity

    @ManyToMany(mappedBy = "services")
    private Set<Client> clients;
}
