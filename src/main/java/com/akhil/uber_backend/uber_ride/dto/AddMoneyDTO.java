package com.akhil.uber_backend.uber_ride.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddMoneyDTO {

    private BigDecimal amount;
}
