package org.example.automanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;
import java.util.UUID;

@Builder
@Entity
@Table(name = "car")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "car", length = 32, nullable = false)
    private String car;

    @Column(name = "model", length = 32, nullable = false)
    private String carModel;

    @Column(name = "color", length = 32, nullable = false)
    private String carColor;

    @Column(name = "year", length = 4, nullable = false)
    private int carModelYear;

    @Column(name = "vin", length = 32, nullable = false)
    private String carVin;

    @Column(name = "fuel_type", length = 32)
    private String fuelType;

    @Column(name = "transmission", length = 32)
    private String transmission;
}
