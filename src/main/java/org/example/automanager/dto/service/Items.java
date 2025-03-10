package org.example.automanager.dto.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.automanager.model.Address;
import org.example.automanager.model.Place;
import org.example.automanager.model.PlaceType;

import java.util.Objects;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Items {
    @JsonProperty("address_comment")
    private String addressComment;
    @JsonProperty("address_name")
    private String addressName;
    @JsonProperty("full_name")
    private String fullName;
    private String id;
    private String name;
    private String type;

    public Address parseAddress() {
        if (Objects.nonNull(fullName)) {
            String[] address = fullName.split(", ");
            return Address.builder()
                    .city(address[0])
                    .street(address[1])
                    .house(address.length < 3 ? "Не указан" : address[2])
                    .build();
        }
        if (Objects.nonNull(addressName)) {
            String[] address = addressName.split(", ");
            return Address.builder()
                    .city("")
                    .street(address[0])
                    .house(address[1])
                    .build();
        }
        return Address.builder().build();
    }

    public Place parsePlace(Address address, String placeType) {
        return Place.builder()
                .name(name)
                .address(address)
                .type(PlaceType.fromStringStrict(placeType))
                .addressComment(addressComment)
                .build();
    }
}