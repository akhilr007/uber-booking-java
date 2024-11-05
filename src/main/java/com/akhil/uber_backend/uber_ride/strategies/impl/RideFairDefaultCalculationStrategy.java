package com.akhil.uber_backend.uber_ride.strategies.impl;

import com.akhil.uber_backend.uber_ride.dto.RideRequestDTO;
import com.akhil.uber_backend.uber_ride.strategies.RideFairCalculationStrategy;
import org.springframework.stereotype.Service;

@Service
public class RideFairDefaultCalculationStrategy implements RideFairCalculationStrategy {

    @Override
    public double calculateFare(RideRequestDTO rideRequestDTO) {
        return 0;
    }
}
