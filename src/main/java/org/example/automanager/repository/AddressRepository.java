package org.example.automanager.repository;

import org.example.automanager.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
            "FROM Address a " +
            "WHERE a.city = :city AND a.street = :street AND a.house = :house")
    boolean isAddressExists(
            @Param("city") String city,
            @Param("street") String street,
            @Param("house") String house
    );

    @Query(value = "SELECT * FROM Address a " +
            "WHERE a.city = :city AND a.street = :street AND a.house = :house", nativeQuery = true)
    Address getByStreetHouseAndCity(
            @Param("city") String city,
            @Param("street") String street,
            @Param("house") String house
    );
}
