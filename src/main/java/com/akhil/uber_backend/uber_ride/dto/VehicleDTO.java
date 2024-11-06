package com.akhil.uber_backend.uber_ride.dto;

import com.akhil.uber_backend.uber_ride.enums.VehicleType;
import lombok.Data;

@Data
public class VehicleDTO {

    private String name;
    private VehicleType vehicleType;
}
