package com.akhil.uber_backend.uber_ride.strategies;

import com.akhil.uber_backend.uber_ride.models.Driver;
import com.akhil.uber_backend.uber_ride.models.RideRequest;

import java.util.List;

@FunctionalInterface
public interface DriverMatchingStrategy {

    List<Driver> findMatchingDrivers(RideRequest rideRequest);
}
