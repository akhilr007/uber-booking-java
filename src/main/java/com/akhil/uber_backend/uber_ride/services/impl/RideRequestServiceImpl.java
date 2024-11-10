package com.akhil.uber_backend.uber_ride.services.impl;

import com.akhil.uber_backend.uber_ride.exceptions.ResourceNotFoundException;
import com.akhil.uber_backend.uber_ride.models.RideRequest;
import com.akhil.uber_backend.uber_ride.repositories.RideRequestRepository;
import com.akhil.uber_backend.uber_ride.services.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {

    private final RideRequestRepository rideRequestRepository;

    @Override
    public RideRequest findRideRequestById(Long rideRequestId) {

        return this.rideRequestRepository.findById(rideRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("RideRequest not found with ID: " + rideRequestId));
    }

    @Override
    public void update(RideRequest rideRequest) {

        RideRequest oldRideRequest = this.rideRequestRepository.findById(rideRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("RideRequest not found with ID " + rideRequest.getId()));

        this.rideRequestRepository.save(oldRideRequest);
    }
}
