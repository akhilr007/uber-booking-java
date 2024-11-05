package com.akhil.uber_backend.uber_ride.dto;

import com.akhil.uber_backend.uber_ride.enums.PaymentMethod;
import com.akhil.uber_backend.uber_ride.enums.RideRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestDTO {

    private Point pickupLocation;

    private Point dropLocation;

    private LocalDateTime requestedTime;

    private RiderDTO rider;

    private PaymentMethod paymentMethod;

    private RideRequestStatus rideRequestStatus;
}
