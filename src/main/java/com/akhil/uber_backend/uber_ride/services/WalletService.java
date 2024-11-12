package com.akhil.uber_backend.uber_ride.services;

import com.akhil.uber_backend.uber_ride.models.User;
import com.akhil.uber_backend.uber_ride.models.Wallet;

import java.math.BigDecimal;

public interface WalletService {

    Wallet addMoneyToWallet(User user, BigDecimal amount);

    void withdrawMoneyFromWallet();

    Wallet findWalletById(Long walletId);

    Wallet createNewWallet(User user);
}
