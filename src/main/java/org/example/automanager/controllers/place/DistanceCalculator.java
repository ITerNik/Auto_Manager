package org.example.automanager.controllers.place;

import java.lang.Math;

public class DistanceCalculator {

    /**
     * Вычисляет расстояние между двумя точками на сфере (например, Земле)
     * используя формулу гаверсинуса.
     *
     * @param lat1 Широта первой точки (в градусах).
     * @param lon1 Долгота первой точки (в градусах).
     * @param lat2 Широта второй точки (в градусах).
     * @param lon2 Долгота второй точки (в градусах).
     * @return Расстояние между двумя точками в километрах.
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Радиус Земли в метрах
        final int R = 6371 * 1000;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
