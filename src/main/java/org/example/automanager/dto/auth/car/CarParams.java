package org.example.automanager.dto.auth.car;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CarParams {
    private int id;

    @JsonProperty("car")
    private String car;

    @JsonProperty("car_model")
    private String carModel;

    @JsonProperty("car_color")
    private String carColor;

    @JsonProperty("car_model_year")
    private int carModelYear;

    @JsonProperty("car_vin")
    private String carVin;

    @JsonProperty("price")
    private String price;

    @JsonProperty("availability")
    private boolean availability;
}
