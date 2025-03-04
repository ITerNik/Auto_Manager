package org.example.automanager.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "promotion_client")
@Data
public class PromotionClient {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToMany
    @Column(name = "client_id", nullable = false)
    private Set<Client> clients;

    @OneToMany
    @Column(name = "promotion_id", nullable = false)
    private Set<Promotion> promotions;

    @Column(name = "end_data", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "qr_code", nullable = false)
    private String qrCode;
}
