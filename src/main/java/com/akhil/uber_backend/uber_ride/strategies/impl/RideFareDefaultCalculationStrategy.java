package com.akhil.uber_backend.uber_ride.strategies.impl;

import com.akhil.uber_backend.uber_ride.models.RideRequest;
import com.akhil.uber_backend.uber_ride.services.DistanceService;
import com.akhil.uber_backend.uber_ride.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RideFareDefaultCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;

    @Override
    public BigDecimal calculateFare(RideRequest rideRequest) {

        double distance = this.distanceService.calculateDistance(rideRequest.getPickupLocation(), rideRequest.getDropLocation());

        return new BigDecimal(distance * RIDE_FARE_MULTIPLIER);
    }
}
