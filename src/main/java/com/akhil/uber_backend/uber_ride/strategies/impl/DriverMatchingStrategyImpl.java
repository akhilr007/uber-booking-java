package com.akhil.uber_backend.uber_ride.strategies.impl;

import com.akhil.uber_backend.uber_ride.dto.RideRequestDTO;
import com.akhil.uber_backend.uber_ride.models.Driver;
import com.akhil.uber_backend.uber_ride.strategies.DriverMatchingStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverMatchingStrategyImpl implements DriverMatchingStrategy {

    @Override
    public List<Driver> findMatchingDrivers(RideRequestDTO rideRequestDTO) {
        return List.of();
    }
}
