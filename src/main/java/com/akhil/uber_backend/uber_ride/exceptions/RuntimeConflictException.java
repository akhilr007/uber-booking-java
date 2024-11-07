package com.akhil.uber_backend.uber_ride.exceptions;

public class RuntimeConflictException extends RuntimeException {

    public RuntimeConflictException() {
    }

    public RuntimeConflictException(String message) {
        super(message);
    }
}
