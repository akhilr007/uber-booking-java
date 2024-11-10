package com.akhil.uber_backend.uber_ride.strategies;

import com.akhil.uber_backend.uber_ride.dto.RideRequestDTO;
import com.akhil.uber_backend.uber_ride.models.RideRequest;

import java.math.BigDecimal;

public interface RideFareCalculationStrategy {

    double RIDE_FARE_MULTIPLIER = 10;

    BigDecimal calculateFare(RideRequest rideRequest);

}
