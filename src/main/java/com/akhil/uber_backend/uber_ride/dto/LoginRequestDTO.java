package com.akhil.uber_backend.uber_ride.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String email;
    private String password;
}