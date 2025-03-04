package org.example.automanager.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "car")
@Data
public class Car {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "fuel_type", length = 32)
    private String fuelType;

    @Column(name = "make", length = 32, nullable = false)
    private String make;

    @Column(name = "model", length = 32, nullable = false)
    private String model;

    @Column(name = "transmission", length = 32)
    private String transmission;

    @Column(name = "year", nullable = false)
    private int year;

    @ManyToMany(mappedBy = "cars")
    private Set<Client> clients;
}
