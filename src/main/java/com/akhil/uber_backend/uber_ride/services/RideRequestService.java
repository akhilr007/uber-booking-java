package com.akhil.uber_backend.uber_ride.services;

import com.akhil.uber_backend.uber_ride.models.RideRequest;

public interface RideRequestService {

    RideRequest findRideRequestById(Long rideRequestId);

    void update(RideRequest rideRequest);
}
