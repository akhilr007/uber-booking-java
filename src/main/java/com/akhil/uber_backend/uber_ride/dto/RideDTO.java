package com.akhil.uber_backend.uber_ride.dto;

import com.akhil.uber_backend.uber_ride.enums.PaymentMethod;
import com.akhil.uber_backend.uber_ride.enums.RideStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RideDTO {

    private PointDTO pickupLocation;

    private PointDTO dropLocation;

    private LocalDateTime createdTime;

    private RiderDTO rider;

    private DriverDTO driver;

    private String otp;

    private PaymentMethod paymentMethod;

    private RideStatus rideStatus;

    private BigDecimal fare;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;
}
