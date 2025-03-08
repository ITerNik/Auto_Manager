package org.example.automanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client")
public class Client implements UserDetails {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "name", length = 32, nullable = false)
    private String name;

    @Column(name = "surname", length = 32, nullable = false)
    private String surname;

    @Column(name = "password", length = 64, nullable = false)
    private String password;

    @Email(message = "Email адрес должен быть в формате user@example.com")
    @Column(name = "email", length = 32, nullable = false, unique = true)
    private String email;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)  // Store the Enum's name as a String
    private Role role;

    @Column(name = "birthday")
    private LocalDateTime birthday;

    @Builder.Default
    @Column(name = "is_blocked", nullable = false)
    private Boolean isBlocked = false; // Default value

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "client_service", // The name of the join table
            joinColumns = @JoinColumn(name = "client_id"), // Column in join table referencing Client
            inverseJoinColumns = @JoinColumn(name = "service_id") // Column in join table referencing Service
    )
    private Set<Service> services;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "client_favorite_service", // The name of the join table
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
