package com.akhil.uber_backend.uber_ride.exceptions;

public class RideRequestException extends RuntimeException{

    public RideRequestException() {
    }

    public RideRequestException(String message) {
        super(message);
    }
}
