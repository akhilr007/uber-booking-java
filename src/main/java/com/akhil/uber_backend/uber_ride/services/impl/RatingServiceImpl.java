package com.akhil.uber_backend.uber_ride.services.impl;

import com.akhil.uber_backend.uber_ride.dto.DriverDTO;
import com.akhil.uber_backend.uber_ride.dto.RiderDTO;
import com.akhil.uber_backend.uber_ride.exceptions.ResourceNotFoundException;
import com.akhil.uber_backend.uber_ride.models.Driver;
import com.akhil.uber_backend.uber_ride.models.Rating;
import com.akhil.uber_backend.uber_ride.models.Ride;
import com.akhil.uber_backend.uber_ride.models.Rider;
import com.akhil.uber_backend.uber_ride.repositories.DriverRepository;
import com.akhil.uber_backend.uber_ride.repositories.RatingRepository;
import com.akhil.uber_backend.uber_ride.repositories.RiderRepository;
import com.akhil.uber_backend.uber_ride.services.RatingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public DriverDTO rateDriver(Ride ride, Integer rating) {

        Rating ratingObj = ratingRepository.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("No ride found with ID " + ride.getId()));

        Driver driver = ride.getDriver();

        ratingObj.setDriverRating(rating);
        ratingRepository.save(ratingObj);

        Double newDriverRating = ratingRepository.findByDriver(driver)
                .stream()
                .mapToDouble(Rating::getDriverRating)
                .average().orElse(0.0);

        driver.setRating(newDriverRating);
        Driver savedDriver = driverRepository.save(driver);

        return modelMapper.map(savedDriver, DriverDTO.class);
    }

    @Override
    @Transactional
    public RiderDTO rateRider(Ride ride, Integer rating) {

        Rating ratingObj = ratingRepository.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("No ride found with ID " + ride.getId()));

        Rider rider = ride.getRider();

        ratingObj.setRiderRating(rating);
        ratingRepository.save(ratingObj);

        Double newRiderRating = ratingRepository.findByRider(rider)
                .stream()
                .mapToDouble(Rating::getRiderRating)
                .average().orElse(0.0);

        rider.setRating(newRiderRating);
        Rider savedRider = riderRepository.save(rider);

        return modelMapper.map(savedRider, RiderDTO.class);

    }

    @Override
    public void createNewRating(Ride ride) {
        Rating rating = Rating.builder()
                .rider(ride.getRider())
                .driver(ride.getDriver())
                .ride(ride)
                .build();
        ratingRepository.save(rating);
    }
}
