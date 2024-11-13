package com.akhil.uber_backend.uber_ride.strategies.impl;

import com.akhil.uber_backend.uber_ride.enums.TransactionMethod;
import com.akhil.uber_backend.uber_ride.models.Driver;
import com.akhil.uber_backend.uber_ride.models.Payment;
import com.akhil.uber_backend.uber_ride.services.WalletService;
import com.akhil.uber_backend.uber_ride.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;

    @Override
    public void processPayment(Payment payment) {

        Driver driver = payment.getRide().getDriver();

        BigDecimal platformCommission = payment.getAmount().multiply(PLATFORM_COMMISSION);

        this.walletService.deductMoneyFromWallet(
                driver.getUser(),
                platformCommission,
                null,
                payment.getRide(),
                TransactionMethod.RIDE);
    }
}
