package com.akhil.uber_backend.uber_ride.exceptions;

public class InsufficientAmountException extends RuntimeException {

    public InsufficientAmountException(){}

    public InsufficientAmountException(String message) {
        super(message);
    }
}
