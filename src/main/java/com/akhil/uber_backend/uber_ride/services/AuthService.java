package com.akhil.uber_backend.uber_ride.services;

import com.akhil.uber_backend.uber_ride.dto.*;
import com.akhil.uber_backend.uber_ride.models.Vehicle;

public interface AuthService {

    String login(String email, String password);

    UserDTO signup(SignupDTO signupDTO);

    DriverDTO onboardNewDriver(Long userId, OnboardDriverDTO onboardDriverDTO);
}
