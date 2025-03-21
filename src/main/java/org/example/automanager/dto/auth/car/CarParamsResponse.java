package org.example.automanager.dto.auth.car;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CarParamsResponse {
    @JsonProperty("cars")
    private List<CarParams> cars;
}
