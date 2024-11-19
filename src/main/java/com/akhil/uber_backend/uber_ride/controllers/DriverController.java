package com.akhil.uber_backend.uber_ride.controllers;

import com.akhil.uber_backend.uber_ride.dto.*;
import com.akhil.uber_backend.uber_ride.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/drivers")
@Secured("ROLE_DRIVER")
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

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDTO> cancelRide(@PathVariable Long rideId){
        return ResponseEntity.ok(this.driverService.cancelRide(rideId));
    }

    @PostMapping("/rateRider/{rideId}")
    public ResponseEntity<RiderDTO> rateRider(@PathVariable Long rideId,
                                              @RequestBody RatingDTO ratingDTO){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.driverService.rateRider(rideId, ratingDTO.getRating()));
    }

    @GetMapping("/get-my-profile")
    public ResponseEntity<DriverDTO> getMyProfile(){
        return ResponseEntity
                .ok(this.driverService.getMyProfile());
    }

    @GetMapping("/get-my-rides")
    public ResponseEntity<Page<RideDTO>> getAllMyRides(@RequestParam(defaultValue = "0") Integer pageOffset,
                                              @RequestParam(defaultValue = "10", required = false) Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageOffset, pageSize,
                Sort.by(Sort.Direction.DESC, "createdTime", "id"));
        return ResponseEntity.ok(this.driverService.getAllMyRides(pageRequest));
    }

}