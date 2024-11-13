package com.akhil.uber_backend.uber_ride.services.impl;

import com.akhil.uber_backend.uber_ride.enums.PaymentStatus;
import com.akhil.uber_backend.uber_ride.models.Payment;
import com.akhil.uber_backend.uber_ride.models.Ride;
import com.akhil.uber_backend.uber_ride.repositories.PaymentRepository;
import com.akhil.uber_backend.uber_ride.services.PaymentService;
import com.akhil.uber_backend.uber_ride.strategies.PaymentStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;

    @Override
    public void processPayment(Payment payment) {
        paymentStrategyManager.paymentStrategy(payment.getPaymentMethod()).processPayment(payment);
    }

    @Override
    public Payment createNewPayment(Ride ride) {
        Payment payment = Payment.builder()
                .ride(ride)
                .paymentMethod(ride.getPaymentMethod())
                .amount(ride.getFare())
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        return paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus) {
        payment.setPaymentStatus(paymentStatus);
        paymentRepository.save(payment);
    }
}
