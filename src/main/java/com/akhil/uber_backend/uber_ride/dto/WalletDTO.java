package com.akhil.uber_backend.uber_ride.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class WalletDTO {

    private Long id;
    private UserDTO user;
    private BigDecimal balance;
    private List<WalletTransactionDTO> transactions;
}
