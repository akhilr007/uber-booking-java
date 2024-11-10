package com.akhil.uber_backend.uber_ride.dto;

import com.akhil.uber_backend.uber_ride.enums.PaymentMethod;
import com.akhil.uber_backend.uber_ride.enums.RideRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestDTO {

    private PointDTO pickupLocation;

    private PointDTO dropLocation;

    private LocalDateTime requestedTime;

    private RiderDTO rider;

    private BigDecimal fare;

    private PaymentMethod paymentMethod;

    private RideRequestStatus rideRequestStatus;
}
