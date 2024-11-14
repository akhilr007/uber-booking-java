package com.akhil.uber_backend.uber_ride.services.impl;

import com.akhil.uber_backend.uber_ride.dto.RideDTO;
import com.akhil.uber_backend.uber_ride.dto.WalletDTO;
import com.akhil.uber_backend.uber_ride.dto.WalletTransactionDTO;
import com.akhil.uber_backend.uber_ride.enums.TransactionMethod;
import com.akhil.uber_backend.uber_ride.enums.TransactionType;
import com.akhil.uber_backend.uber_ride.exceptions.ResourceNotFoundException;
import com.akhil.uber_backend.uber_ride.models.Ride;
import com.akhil.uber_backend.uber_ride.models.User;
import com.akhil.uber_backend.uber_ride.models.Wallet;
import com.akhil.uber_backend.uber_ride.models.WalletTransaction;
import com.akhil.uber_backend.uber_ride.repositories.WalletRepository;
import com.akhil.uber_backend.uber_ride.services.WalletService;
import com.akhil.uber_backend.uber_ride.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final ModelMapper modelMapper;
    private final WalletTransactionService walletTransactionService;

    @Override
    @Transactional
    public Wallet addMoneyToWallet(User user, BigDecimal amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {

        Wallet wallet = this.findByUser(user);
        wallet.setBalance(wallet.getBalance().add(amount));

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

        this.walletTransactionService.createNewWalletTransaction(walletTransaction);
        return this.walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public Wallet deductMoneyFromWallet(User user, BigDecimal amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {
        Wallet wallet = this.findByUser(user);
        wallet.setBalance(wallet.getBalance().subtract(amount));

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

        this.walletTransactionService.createNewWalletTransaction(walletTransaction);
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
    public Wallet findByUser(User user) {
        return this.walletRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found for the user with ID: " + user.getId()));
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
