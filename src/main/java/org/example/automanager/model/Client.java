package org.example.automanager.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "client")
@Data
public class Client {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "name", length = 32, nullable = false)
    private String name;

    @Column(name = "surname", length = 32, nullable = false)
    private String surname;

    @Column(name = "password", length = 64, nullable = false)
    private String password;

    @Column(name = "email", length = 32, nullable = false, unique = true)
    private String email;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)  // Store the Enum's name as a String
    private Role role;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "is_blocked", nullable = false)
    private Boolean isBlocked = false; // Default value

    @ManyToMany
    @JoinTable(
            name = "client_service", // The name of the join table
            joinColumns = @JoinColumn(name = "client_id"), // Column in join table referencing Client
            inverseJoinColumns = @JoinColumn(name = "service_id") // Column in join table referencing Service
    )
    private Set<Service> services;

    @ManyToMany
    @JoinTable(
            name = "client_favorite_services", // The name of the join table
            joinColumns = @JoinColumn(name = "client_id"), // Column in join table referencing Client
            inverseJoinColumns = @JoinColumn(name = "service_id") // Column in join table referencing Service
    )
    private Set<Service> favoriteServices;

    @ManyToMany
    @JoinTable(
            name = "client_car",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id")
    )
    private Set<Car> cars;
}
