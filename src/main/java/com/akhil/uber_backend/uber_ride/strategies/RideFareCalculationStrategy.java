package com.akhil.uber_backend.uber_ride.strategies;

import com.akhil.uber_backend.uber_ride.dto.RideRequestDTO;

public interface RideFareCalculationStrategy {

    double calculateFare(RideRequestDTO rideRequestDTO);

}
