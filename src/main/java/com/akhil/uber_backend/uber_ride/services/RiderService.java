package com.akhil.uber_backend.uber_ride.services;

import com.akhil.uber_backend.uber_ride.dto.DriverDTO;
import com.akhil.uber_backend.uber_ride.dto.RideDTO;
import com.akhil.uber_backend.uber_ride.dto.RideRequestDTO;
import com.akhil.uber_backend.uber_ride.dto.RiderDTO;

import java.util.List;

public interface RiderService {

    RideRequestDTO requestRide(RideRequestDTO rideRequestDTO);

    RideDTO cancelRide(Long rideId);

    DriverDTO rateDriver(Long rideId, Integer rating);

    RiderDTO getMyProfile();

    List<RideDTO> getAllMyRides();
}
