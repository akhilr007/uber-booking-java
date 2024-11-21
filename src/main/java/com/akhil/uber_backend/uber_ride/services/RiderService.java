package com.akhil.uber_backend.uber_ride.services;

import com.akhil.uber_backend.uber_ride.dto.*;
import com.akhil.uber_backend.uber_ride.models.Rider;
import com.akhil.uber_backend.uber_ride.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

public interface RiderService {

    Rider createNewRider(User user);

    RideRequestDTO requestRide(RideRequestDTO rideRequestDTO);

    RideDTO cancelRide(Long rideId);

    DriverDTO rateDriver(Long rideId, Integer rating);

    RiderDTO getMyProfile();

    Page<RideDTO> getAllMyRides(PageRequest pageRequest);

    Rider getCurrentRider();

    WalletDTO addMoneyToWallet(BigDecimal amount);
}