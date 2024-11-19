package com.akhil.uber_backend.uber_ride.controllers;

import com.akhil.uber_backend.uber_ride.dto.*;
import com.akhil.uber_backend.uber_ride.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/riders")
@Secured("ROLE_RIDER")
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

    @PostMapping("/rateDriver/{rideId}")
    public ResponseEntity<DriverDTO> rateDriver(@PathVariable Long rideId,
                                                @RequestBody RatingDTO ratingDTO){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.riderService.rateDriver(rideId, ratingDTO.getRating()));
    }

    @GetMapping("/get-my-profile")
    public ResponseEntity<RiderDTO> getMyProfile(){
        return ResponseEntity
                .ok(this.riderService.getMyProfile());
    }

    @GetMapping("/get-my-rides")
    public ResponseEntity<Page<RideDTO>> getAllMyRides(@RequestParam(defaultValue = "0") Integer pageOffset,
                                                       @RequestParam(defaultValue = "10", required = false) Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageOffset, pageSize);
        return ResponseEntity.ok(this.riderService.getAllMyRides(pageRequest));
    }

    @PostMapping("/wallet/add-money/{userId}")
    public ResponseEntity<WalletDTO> addMoneyToWallet(@PathVariable Long userId,
                                                      @RequestBody AddMoneyDTO addMoneyDTO){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.riderService.addMoneyToWallet(userId, addMoneyDTO.getAmount()));
    }

}