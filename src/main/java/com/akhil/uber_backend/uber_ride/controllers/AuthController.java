package com.akhil.uber_backend.uber_ride.controllers;

import com.akhil.uber_backend.uber_ride.dto.SignupDTO;
import com.akhil.uber_backend.uber_ride.dto.UserDTO;
import com.akhil.uber_backend.uber_ride.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    UserDTO signUp(@RequestBody SignupDTO signupDTO){
        return authService.signup(signupDTO);
    }
}
