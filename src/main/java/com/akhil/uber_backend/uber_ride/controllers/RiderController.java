package com.akhil.uber_backend.uber_ride.controllers;

import com.akhil.uber_backend.uber_ride.dto.RideDTO;
import com.akhil.uber_backend.uber_ride.dto.RideRequestDTO;
import com.akhil.uber_backend.uber_ride.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/riders")
public class RiderController {

    private final RiderService riderService;

    @PostMapping("/requestRide")
    public ResponseEntity<RideRequestDTO> requestRide(@RequestBody RideRequestDTO rideRequestDTO){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.riderService.requestRide(rideRequestDTO));

    }

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDTO> cancelRide(@PathVariable Long rideId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.riderService.cancelRide(rideId));
    }
}
