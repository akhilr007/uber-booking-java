package com.akhil.uber_backend.uber_ride.dto;

import lombok.Data;

@Data
public class PointDTO {

    private double[] coordinates;
    private String type = "Point";
}
