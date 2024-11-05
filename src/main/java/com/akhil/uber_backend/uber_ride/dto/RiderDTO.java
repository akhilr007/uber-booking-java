package com.akhil.uber_backend.uber_ride.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiderDTO {

    private UserDTO userDTO;
    private Double rating;
}