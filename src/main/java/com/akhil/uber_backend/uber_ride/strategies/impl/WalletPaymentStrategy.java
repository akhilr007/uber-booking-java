package com.akhil.uber_backend.uber_ride.strategies.impl;

import com.akhil.uber_backend.uber_ride.enums.PaymentStatus;
import com.akhil.uber_backend.uber_ride.enums.TransactionMethod;
import com.akhil.uber_backend.uber_ride.exceptions.InsufficientAmountException;
import com.akhil.uber_backend.uber_ride.models.Driver;
import com.akhil.uber_backend.uber_ride.models.Payment;
import com.akhil.uber_backend.uber_ride.models.Rider;
import com.akhil.uber_backend.uber_ride.models.Wallet;
import com.akhil.uber_backend.uber_ride.repositories.PaymentRepository;
import com.akhil.uber_backend.uber_ride.services.WalletService;
import com.akhil.uber_backend.uber_ride.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void processPayment(Payment payment) {

        Rider rider = payment.getRide().getRider();
        BigDecimal amountToBePaid = payment.getAmount();
        Wallet riderWallet = this.walletService.findWalletById(rider.getId());

        // top up rider wallet if necessary
        if(riderWallet.getBalance().compareTo(amountToBePaid) < 0){
            BigDecimal amountRequired = amountToBePaid.subtract(riderWallet.getBalance());
            throw new InsufficientAmountException(String.format("Rs. %.2f amount is required for the ride payment. Add money to wallet", amountRequired));
        }

        walletService.deductMoneyFromWallet(
                rider.getUser(),
                amountToBePaid,
                null,
                payment.getRide(),
                TransactionMethod.RIDE
        );


        Driver driver = payment.getRide().getDriver();
        BigDecimal driverCutAfterCommissionDeduction = payment.getAmount().multiply(BigDecimal.ONE.subtract(PLATFORM_COMMISSION));

        this.walletService.addMoneyToWallet(
                driver.getUser(),
                driverCutAfterCommissionDeduction,
                null,
                payment.getRide(),
                TransactionMethod.RIDE
        );

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
