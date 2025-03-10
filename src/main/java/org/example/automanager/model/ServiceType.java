package org.example.automanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ServiceType {
    MAINTENANCE("Обслуживание"),
    WASH("Мойка"),
    TIRE_CHANGE("Замена шин"),
    OIL_CHANGE("Замена масла");

    private final String displayName;

    public static ServiceType fromStringStrict(String text) {
        return Arrays.stream(ServiceType.values())
                .filter(serviceType -> serviceType.name().equalsIgnoreCase(text))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return this.name();
    }
}
