package org.example.automanager.repository;

import org.example.automanager.model.Place;
import org.example.automanager.model.PlaceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlaceRepository extends JpaRepository<Place, UUID> {
    @Query(value = "SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
            "FROM place p " +
            "WHERE p.name = :name AND p.type = :type AND p.address_id = :addressId", nativeQuery = true)
    boolean isPlaceExists(
            @Param("name") String name,
            @Param("type") String placeType,
            @Param("addressId") UUID addressId
    );

    @Query(value = "SELECT * FROM place p " +
            "WHERE p.name = :name AND p.type = :type AND p.address_id = :addressId", nativeQuery = true)
    Place getByNameTypeAndAddressId(
            @Param("name") String name,
            @Param("type") String placeType,
            @Param("addressId") UUID addressId
    );

    List<Place> findByNameContainingIgnoreCase(String name);
}
