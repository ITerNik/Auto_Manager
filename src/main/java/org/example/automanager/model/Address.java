package org.example.automanager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "address")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Setter
    @Column(name = "city", length = 32)
    private String city;

    @Column(name = "street", length = 32, nullable = false)
    private String street;

    @Column(name = "house", nullable = false)
    private String house;
}
