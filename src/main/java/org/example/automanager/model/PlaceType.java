package org.example.automanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PlaceType {
    GAS_STATION("Заправка"),
    ELECTRIC_REFUELING("Электрическая заправка"),
    CAR_WASH("Автомойка"),
    CAR_SERVICE("Автосервис");

    private final String displayName;

    public static PlaceType fromStringStrict(String text) {
        return Arrays.stream(PlaceType.values())
                .filter(placeType -> placeType.name().equalsIgnoreCase(text))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return displayName; // Возвращает имя Enum, а не display Name
    }
}
