package com.akhil.uber_backend.uber_ride.services;

import com.akhil.uber_backend.uber_ride.enums.TransactionMethod;
import com.akhil.uber_backend.uber_ride.models.Ride;
import com.akhil.uber_backend.uber_ride.models.User;
import com.akhil.uber_backend.uber_ride.models.Wallet;

import java.math.BigDecimal;

public interface WalletService {

    Wallet addMoneyToWallet(User user, BigDecimal amount, String transactionId, Ride ride, TransactionMethod transactionMethod);

    Wallet deductMoneyFromWallet(User user, BigDecimal amount, String transactionId, Ride ride, TransactionMethod transactionMethod);

    void withdrawMoneyFromWallet();

    Wallet findWalletById(Long walletId);

    Wallet findByUser(User user);

    Wallet createNewWallet(User user);
}
