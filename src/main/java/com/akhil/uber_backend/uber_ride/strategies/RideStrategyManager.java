package com.akhil.uber_backend.uber_ride.strategies;

import com.akhil.uber_backend.uber_ride.strategies.impl.DriverMatchingHighestRatedDriverStrategy;
import com.akhil.uber_backend.uber_ride.strategies.impl.DriverMatchingNearestDriverStrategy;
import com.akhil.uber_backend.uber_ride.strategies.impl.RideFareDefaultCalculationStrategy;
import com.akhil.uber_backend.uber_ride.strategies.impl.RideFareSurgePricingCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {

    private final DriverMatchingHighestRatedDriverStrategy highestRatedDriverStrategy;
    private final DriverMatchingNearestDriverStrategy nearestDriverStrategy;
    private final RideFareSurgePricingCalculationStrategy surgePricingCalculationStrategy;
    private final RideFareDefaultCalculationStrategy defaultCalculationStrategy;

    public DriverMatchingStrategy driverMatchingStrategy(double riderRating){

        if(riderRating >= 4.5){
            return highestRatedDriverStrategy;
        }

        return nearestDriverStrategy;
    }

    public RideFareCalculationStrategy rideFareCalculationStrategy(){

        // 6PM - 9PM : Surge Time
        LocalTime surgeStartTime = LocalTime.of(18, 00);
        LocalTime surgeEndTime = LocalTime.of(21, 00);
        LocalTime currentTime = LocalTime.now();

        boolean isSurgeTime = currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);

        if(isSurgeTime){
            return surgePricingCalculationStrategy;
        }

        return defaultCalculationStrategy;
    }
}
