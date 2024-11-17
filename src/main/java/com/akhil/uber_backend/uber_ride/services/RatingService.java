package com.akhil.uber_backend.uber_ride.services;

import com.akhil.uber_backend.uber_ride.dto.DriverDTO;
import com.akhil.uber_backend.uber_ride.dto.RiderDTO;
import com.akhil.uber_backend.uber_ride.models.Driver;
import com.akhil.uber_backend.uber_ride.models.Ride;
import com.akhil.uber_backend.uber_ride.models.Rider;

public interface RatingService {

    DriverDTO rateDriver(Ride ride, Integer rating);

    RiderDTO rateRider(Ride ride, Integer rating);

    void createNewRating(Ride ride);
}
