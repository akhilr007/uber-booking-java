package com.akhil.uber_backend.uber_ride.services;

import com.akhil.uber_backend.uber_ride.dto.WalletTransactionDTO;

public interface WalletTransactionService {

    void createNewWalletTransaction(WalletTransactionDTO walletTransactionDTO);
}
