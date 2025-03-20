package org.example.automanager.repository;

import org.example.automanager.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlaceRepository extends JpaRepository<Place, UUID> {

    @Procedure(name = "is_place_exists")
    boolean isPlaceExists(
            @Param("p_name") String name,
            @Param("p_type") String placeType,
            @Param("p_address_id") UUID addressId
    );

    @Procedure(name = "get_place_by_name_type_address")
    Place getByNameTypeAndAddressId(
            @Param("p_name") String name,
            @Param("p_type") String placeType,
            @Param("p_address_id") UUID addressId
    );

    List<Place> findByNameContainingIgnoreCase(String name);
}
