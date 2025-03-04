package org.example.automanager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "address")
@Data
public class Address {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "city", length = 32, nullable = false)
    private String city;

    @Column(name = "street", length = 32, nullable = false)
    private String street;

    @Column(name = "house", nullable = false)
    private Integer house;

    @Column(name = "building", nullable = false)
    private Integer building;
}
