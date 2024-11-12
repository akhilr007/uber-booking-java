package com.akhil.uber_backend.uber_ride.dto;

import com.akhil.uber_backend.uber_ride.enums.TransactionMethod;
import com.akhil.uber_backend.uber_ride.enums.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WalletTransactionDTO {

    private Long id;
    private Double amount;
    private TransactionType transactionType;
    private TransactionMethod transactionMethod;
    private RiderDTO ride;
    private String transactionId;
    private WalletDTO wallet;
    private LocalDateTime timestamp;
}
