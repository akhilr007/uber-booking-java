package com.akhil.uber_backend.uber_ride.services.impl;

import com.akhil.uber_backend.uber_ride.dto.DriverDTO;
import com.akhil.uber_backend.uber_ride.dto.RideDTO;
import com.akhil.uber_backend.uber_ride.dto.RideRequestDTO;
import com.akhil.uber_backend.uber_ride.dto.RiderDTO;
import com.akhil.uber_backend.uber_ride.enums.RideRequestStatus;
import com.akhil.uber_backend.uber_ride.models.RideRequest;
import com.akhil.uber_backend.uber_ride.models.Rider;
import com.akhil.uber_backend.uber_ride.models.User;
import com.akhil.uber_backend.uber_ride.repositories.RideRequestRepository;
import com.akhil.uber_backend.uber_ride.repositories.RiderRepository;
import com.akhil.uber_backend.uber_ride.services.RiderService;
import com.akhil.uber_backend.uber_ride.strategies.DriverMatchingStrategy;
import com.akhil.uber_backend.uber_ride.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideFareCalculationStrategy rideFareCalculationStrategy;
    private final DriverMatchingStrategy driverMatchingStrategy;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;

    @Override
    public Rider createNewRider(User user) {

        Rider rider = Rider.builder()
                .user(user)
                .rating(0.0)
                .build();

       return riderRepository.save(rider);
    }

    @Override
    public RideRequestDTO requestRide(RideRequestDTO rideRequestDTO) {

        RideRequest rideRequest = modelMapper.map(rideRequestDTO, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);

        Double fare = rideFareCalculationStrategy.calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

        driverMatchingStrategy.findMatchingDrivers(rideRequest);

        return modelMapper.map(savedRideRequest, RideRequestDTO.class);
    }

    @Override
    public RideDTO cancelRide(Long rideId) {
        return null;
    }

    @Override
    public DriverDTO rateDriver(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RiderDTO getMyProfile() {
        return null;
    }

    @Override
    public List<RideDTO> getAllMyRides() {
        return List.of();
    }
}
