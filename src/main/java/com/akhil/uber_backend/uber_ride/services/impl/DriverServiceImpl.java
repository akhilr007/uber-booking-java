package com.akhil.uber_backend.uber_ride.services.impl;

import com.akhil.uber_backend.uber_ride.dto.DriverDTO;
import com.akhil.uber_backend.uber_ride.dto.RideDTO;
import com.akhil.uber_backend.uber_ride.dto.RiderDTO;
import com.akhil.uber_backend.uber_ride.enums.RideRequestStatus;
import com.akhil.uber_backend.uber_ride.enums.RideStatus;
import com.akhil.uber_backend.uber_ride.exceptions.DriverNotAvailableException;
import com.akhil.uber_backend.uber_ride.exceptions.ResourceNotFoundException;
import com.akhil.uber_backend.uber_ride.exceptions.RideRequestException;
import com.akhil.uber_backend.uber_ride.models.Driver;
import com.akhil.uber_backend.uber_ride.models.Ride;
import com.akhil.uber_backend.uber_ride.models.RideRequest;
import com.akhil.uber_backend.uber_ride.repositories.DriverRepository;
import com.akhil.uber_backend.uber_ride.services.DriverService;
import com.akhil.uber_backend.uber_ride.services.PaymentService;
import com.akhil.uber_backend.uber_ride.services.RideRequestService;
import com.akhil.uber_backend.uber_ride.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;

    @Override
    @Transactional
    public RideDTO acceptRide(Long rideRequestId) {

        RideRequest rideRequest = this.rideRequestService.findRideRequestById(rideRequestId);

        if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)){
            throw new RideRequestException("RideRequest is cancelled or created with ID: " + rideRequestId);
        }

        Driver currentDriver = getCurrentDriver();
        if(!currentDriver.getAvailable()) {
            throw new DriverNotAvailableException("Driver cannot accept ride due to unavailability: "
                    + currentDriver.getId());
        }

        Driver savedDriver = this.updateDriverAvailability(currentDriver, false);

        Ride ride = this.rideService.createNewRide(rideRequest, savedDriver);

        return this.modelMapper.map(ride, RideDTO.class);
    }

    @Override
    public RideDTO cancelRide(Long rideId) {

        Ride ride = this.rideService.getRideById(rideId);

        Driver driver = getCurrentDriver();
        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot cancel the ride as he did not accepted it earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride cannot be cancelled, Invalid status: " + ride.getRideStatus());
        }

        this.rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        this.updateDriverAvailability(driver, true);

        return this.modelMapper.map(ride, RideDTO.class);
    }

    @Override
    public RideDTO startRide(Long rideId, String otp) {

        Ride ride = this.rideService.getRideById(rideId);

        Driver driver = getCurrentDriver();
        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot start the ride as he not accepted it earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride Status is not confirmed hence cannot be started, STATUS: " + ride.getRideStatus());
        }

        if(!otp.equals(ride.getOtp())){
            throw new RuntimeException("OTP is not valid");
        }

        ride.setStartedAt(LocalDateTime.now());
        Ride savedRide = this.rideService.updateRideStatus(ride, RideStatus.ONGOING);

        this.paymentService.createNewPayment(savedRide);

        return modelMapper.map(savedRide, RideDTO.class);
    }

    @Override
    @Transactional
    public RideDTO endRide(Long rideId) {

        Ride ride = this.rideService.getRideById(rideId);

        Driver driver = getCurrentDriver();
        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot end the ride as he has not accepted it earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.ONGOING)){
            throw new RuntimeException("Ride Status is not ONGOING hence cannot be ended, STATUS: " + ride.getRideStatus());
        }

        ride.setEndedAt(LocalDateTime.now());
        Ride savedRide = this.rideService.updateRideStatus(ride, RideStatus.ENDED);
        updateDriverAvailability(driver, true);

        this.paymentService.processPayment(ride);

        return this.modelMapper.map(savedRide, RideDTO.class);
    }

    @Override
    public RiderDTO rateRider(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public DriverDTO getMyProfile() {
        Driver currentDriver = this.getCurrentDriver();
        return this.modelMapper.map(currentDriver, DriverDTO.class);
    }

    @Override
    public Page<RideDTO> getAllMyRides(PageRequest pageRequest) {

        Driver driver = this.getCurrentDriver();
        return this.rideService.getAllRidesOfDriver(driver, pageRequest)
                .map(ride -> this.modelMapper.map(ride, RideDTO.class));
    }

    @Override
    public Driver getCurrentDriver() {
        return this.driverRepository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("Current driver not found"));
    }

    @Override
    public Driver updateDriverAvailability(Driver driver, boolean available) {
        driver.setAvailable(available);
        return this.driverRepository.save(driver);
    }

    @Override
    public Driver createNewDriver(Driver driver) {
        return this.driverRepository.save(driver);
    }


}
