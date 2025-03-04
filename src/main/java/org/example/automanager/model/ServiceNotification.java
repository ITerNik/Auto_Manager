package org.example.automanager.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "service_notification")
@Data
public class ServiceNotification {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "service_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    @Column(name = "notification_date", nullable = false)
    private LocalDateTime notificationDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "comment")
    private String comment;
}
