package com.akhil.uber_backend.uber_ride.controllers;

import com.akhil.uber_backend.uber_ride.dto.RideDTO;
import com.akhil.uber_backend.uber_ride.dto.RideStartDTO;
import com.akhil.uber_backend.uber_ride.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;

    @PostMapping("/acceptRide/{rideRequestId}")
    public ResponseEntity<RideDTO> acceptRide(@PathVariable Long rideRequestId){
        return ResponseEntity.ok(this.driverService.acceptRide(rideRequestId));
    }

    @PostMapping("/startRide/{rideId}")
    public ResponseEntity<RideDTO> startRide(@PathVariable Long rideId,
                                              @RequestBody RideStartDTO rideStartDTO){

        return ResponseEntity.ok(this.driverService.startRide(rideId, rideStartDTO.getOtp()));
    }

    @PostMapping("/endRide/{rideId}")
    public ResponseEntity<RideDTO> endRide(@PathVariable Long rideId){
        return ResponseEntity.ok(this.driverService.endRide(rideId));
    }

}
