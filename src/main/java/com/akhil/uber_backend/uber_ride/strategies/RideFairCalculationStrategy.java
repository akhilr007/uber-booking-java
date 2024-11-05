package com.akhil.uber_backend.uber_ride.strategies;

import com.akhil.uber_backend.uber_ride.dto.RideRequestDTO;

public interface RideFairCalculationStrategy {

    double calculateFare(RideRequestDTO rideRequestDTO);

}
