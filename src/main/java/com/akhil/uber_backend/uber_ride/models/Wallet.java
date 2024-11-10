package com.akhil.uber_backend.uber_ride.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    private BigDecimal balance;

    @OneToMany(mappedBy = "wallet", fetch = FetchType.LAZY)
    private List<WalletTransaction> transactions;

}
