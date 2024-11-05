package com.akhil.uber_backend.uber_ride.dto;

import com.akhil.uber_backend.uber_ride.enums.PaymentMethod;
import com.akhil.uber_backend.uber_ride.enums.RideStatus;
import com.akhil.uber_backend.uber_ride.models.Driver;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

public class RideDTO {

    private Point pickupLocation;

    private Point dropLocation;

    private LocalDateTime createdTime;

    private RiderDTO rider;

    private DriverDTO driver;

    private PaymentMethod paymentMethod;

    private RideStatus rideStatus;

    private Double fare;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;
}
