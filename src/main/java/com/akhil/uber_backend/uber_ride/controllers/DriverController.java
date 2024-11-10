package com.akhil.uber_backend.uber_ride.controllers;

import com.akhil.uber_backend.uber_ride.dto.RideDTO;
import com.akhil.uber_backend.uber_ride.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;

    @PostMapping("/acceptRide/{rideRequestId}")
    public ResponseEntity<RideDTO> acceptRide(@PathVariable Long rideRequestId){
        return ResponseEntity.ok(this.driverService.acceptRide(rideRequestId));
    }

}
