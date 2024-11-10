package com.akhil.uber_backend.uber_ride.exceptions;

public class DriverNotAvailableException extends RuntimeException{

    public DriverNotAvailableException() {
    }

    public DriverNotAvailableException(String message) {
        super(message);
    }
}
