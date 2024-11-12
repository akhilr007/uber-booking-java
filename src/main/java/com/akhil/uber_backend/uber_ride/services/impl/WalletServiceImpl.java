package com.akhil.uber_backend.uber_ride.services.impl;

import com.akhil.uber_backend.uber_ride.exceptions.ResourceNotFoundException;
import com.akhil.uber_backend.uber_ride.models.User;
import com.akhil.uber_backend.uber_ride.models.Wallet;
import com.akhil.uber_backend.uber_ride.repositories.WalletRepository;
import com.akhil.uber_backend.uber_ride.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public Wallet addMoneyToWallet(User user, BigDecimal amount) {

        Wallet wallet = this.walletRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found for user with ID: " + user.getId()));

        wallet.setBalance(wallet.getBalance().add(amount));

        return this.walletRepository.save(wallet);
    }

    @Override
    public void withdrawMoneyFromWallet() {

    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return this.walletRepository.findById(walletId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found with ID: " + walletId));
    }

    @Override
    public Wallet createNewWallet(User user) {
        Wallet wallet =  Wallet.builder()
                .user(user)
                .balance(new BigDecimal(0.0))
                .build();

        return this.walletRepository.save(wallet);
    }
}
