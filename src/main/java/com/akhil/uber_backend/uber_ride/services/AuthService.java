package com.akhil.uber_backend.uber_ride.services;

import com.akhil.uber_backend.uber_ride.dto.*;

public interface AuthService {

    LoginResponseDTO login(String email, String password);

    UserDTO signup(SignupDTO signupDTO);

    DriverDTO onboardNewDriver(Long userId, OnboardDriverDTO onboardDriverDTO);
}