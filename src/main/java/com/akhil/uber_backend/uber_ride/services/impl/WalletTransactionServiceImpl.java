package com.akhil.uber_backend.uber_ride.services.impl;

import com.akhil.uber_backend.uber_ride.models.WalletTransaction;
import com.akhil.uber_backend.uber_ride.repositories.WalletTransactionRepository;
import com.akhil.uber_backend.uber_ride.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;

    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {

        this.walletTransactionRepository.save(walletTransaction);
    }
}
