package org.example.automanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;
import java.util.UUID;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "is_place_exists",
                procedureName = "is_place_exists",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_name", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_type", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_address_id", type = UUID.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "result", type = Boolean.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "get_place_by_name_type_address",
                procedureName = "get_place_by_name_type_address",
                resultClasses = Place.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_name", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_type", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_address_id", type = UUID.class)
                }
        )
})
@Entity
@Table(name = "place")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PlaceType type;

    @Column(name = "address_comment", columnDefinition = "TEXT")
    private String addressComment;
}
