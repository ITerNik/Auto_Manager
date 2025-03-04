package org.example.automanager.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "place")
@Data
public class Place {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", length = 32, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PlaceType type;

    @Column(name = "address_comment", columnDefinition = "TEXT")
    private String addressComment;
}
