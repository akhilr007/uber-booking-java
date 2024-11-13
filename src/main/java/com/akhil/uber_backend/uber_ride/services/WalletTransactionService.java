package com.akhil.uber_backend.uber_ride.services;

import com.akhil.uber_backend.uber_ride.models.WalletTransaction;

public interface WalletTransactionService {

    void createNewWalletTransaction(WalletTransaction walletTransaction);
}
