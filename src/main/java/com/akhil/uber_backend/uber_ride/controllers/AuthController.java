package com.akhil.uber_backend.uber_ride.controllers;

import com.akhil.uber_backend.uber_ride.dto.*;
import com.akhil.uber_backend.uber_ride.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    ResponseEntity<UserDTO> signUp(@RequestBody SignupDTO signupDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.signup(signupDTO));
    }

    @PostMapping("/login")
    ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response){

        LoginResponseDTO loginResponseDTO = authService
                .login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

        Cookie accessToken = new Cookie("accessToken", loginResponseDTO.getAccessToken());
        accessToken.setHttpOnly(true);
        response.addCookie(accessToken);

        Cookie refreshToken = new Cookie("refreshToken", loginResponseDTO.getRefreshToken());
        refreshToken.setHttpOnly(true);
        response.addCookie(refreshToken);

        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/onboard-new-driver/{userId}")
    ResponseEntity<DriverDTO> onboardNewDriver(@PathVariable Long userId,
                                               @RequestBody OnboardDriverDTO onboardDriverDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.onboardNewDriver(userId, onboardDriverDTO));
    }
}