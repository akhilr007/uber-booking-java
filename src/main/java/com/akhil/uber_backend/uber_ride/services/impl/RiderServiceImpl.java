package com.akhil.uber_backend.uber_ride.services.impl;

import com.akhil.uber_backend.uber_ride.dto.*;
import com.akhil.uber_backend.uber_ride.enums.RideRequestStatus;
import com.akhil.uber_backend.uber_ride.enums.RideStatus;
import com.akhil.uber_backend.uber_ride.enums.TransactionMethod;
import com.akhil.uber_backend.uber_ride.exceptions.ResourceNotFoundException;
import com.akhil.uber_backend.uber_ride.models.*;
import com.akhil.uber_backend.uber_ride.repositories.RideRequestRepository;
import com.akhil.uber_backend.uber_ride.repositories.RiderRepository;
import com.akhil.uber_backend.uber_ride.repositories.UserRepository;
import com.akhil.uber_backend.uber_ride.repositories.WalletRepository;
import com.akhil.uber_backend.uber_ride.services.*;
import com.akhil.uber_backend.uber_ride.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {
    private final WalletRepository walletRepository;

    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideService rideService;
    private final DriverService driverService;
    private final RatingService ratingService;
    private final WalletService walletService;
    private final UserRepository userRepository;

    @Override
    public Rider createNewRider(User user) {

        Rider rider = Rider.builder()
                .user(user)
                .rating(0.0)
                .build();

       return this.riderRepository.save(rider);
    }

    @Override
    @Transactional
    public RideRequestDTO requestRide(RideRequestDTO rideRequestDTO) {

        Rider currentRider = getCurrentRider();

        RideRequest rideRequest = this.modelMapper.map(rideRequestDTO, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(currentRider);

        BigDecimal fare = this.rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare.setScale(2, RoundingMode.HALF_UP));

        RideRequest savedRideRequest = this.rideRequestRepository.save(rideRequest);

        List< Driver> drivers = this.rideStrategyManager
                .driverMatchingStrategy(currentRider.getRating()).findMatchingDrivers(rideRequest);

        // TODO : send notification to all drivers

        return this.modelMapper.map(savedRideRequest, RideRequestDTO.class);
    }

    @Override
    public RideDTO cancelRide(Long rideId) {

        Rider rider = this.getCurrentRider();
        Ride ride = this.rideService.getRideById(rideId);

        if(!rider.equals(ride.getRider())){
            throw new IllegalArgumentException("Rider cant cancel the ride as it was not booked by him/her");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride cannot be cancelled, Invalid status: " + ride.getRideStatus());
        }

        Ride cancelledRide = this.rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        this.driverService.updateDriverAvailability(ride.getDriver(), true);

        return this.modelMapper.map(cancelledRide, RideDTO.class);
    }

    @Override
    public DriverDTO rateDriver(Long rideId, Integer rating) {

        Ride ride = this.rideService.getRideById(rideId);
        Rider rider = getCurrentRider();

        if(!rider.equals(ride.getRider())){
            throw new RuntimeException("Rider is not owner of this ride");
        }

        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("You cannot rate the ride as it has not ended");
        }

        return ratingService.rateDriver(ride, rating);
    }

    @Override
    public RiderDTO getMyProfile() {
        Rider rider = this.getCurrentRider();
        return this.modelMapper.map(rider, RiderDTO.class);
    }

    @Override
    public Page<RideDTO> getAllMyRides(PageRequest pageRequest) {

        Rider rider = this.getCurrentRider();
        return this.rideService.getAllRidesOfRider(rider, pageRequest)
                .map(ride -> this.modelMapper.map(ride, RideDTO.class));
    }

    @Override
    public Rider getCurrentRider() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.riderRepository.findByUser(user).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Rider not associated with ID: %d", user.getId()))
        );
    }

    @Override
    public WalletDTO addMoneyToWallet(BigDecimal amount) {

        User user = getCurrentRider().getUser();

        Wallet wallet = this.walletService.addMoneyToWallet(
                        user,
                        amount,
                        null,
                        null,
                        TransactionMethod.BANKING);

        return modelMapper.map(wallet, WalletDTO.class);
    }
}