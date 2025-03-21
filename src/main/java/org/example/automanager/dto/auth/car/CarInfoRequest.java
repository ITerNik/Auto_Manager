package org.example.automanager.dto.auth.car;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarInfoRequest {
    private String car;
    private String carModel;
    private String carColor;
    private String carVin;
    private String fuelType;
    private String transmission;
}
