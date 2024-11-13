package com.akhil.uber_backend.uber_ride.strategies;

import com.akhil.uber_backend.uber_ride.models.Payment;

import java.math.BigDecimal;

public interface PaymentStrategy {

    static final BigDecimal PLATFORM_COMMISSION = BigDecimal.valueOf(0.3);

    void processPayment(Payment payment);
}
