package org.example.automanager.dto.car;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarInfoRequest {
    private String car;
    private String carModel;
    private String carColor;
    private Integer carModelYear;
    private String carVin;
    private String fuelType;
    private String transmission;
}
