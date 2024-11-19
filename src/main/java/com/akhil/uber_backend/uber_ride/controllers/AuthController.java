package com.akhil.uber_backend.uber_ride.controllers;

import com.akhil.uber_backend.uber_ride.dto.*;
import com.akhil.uber_backend.uber_ride.services.AuthService;
import com.akhil.uber_backend.uber_ride.services.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final SessionService sessionService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignupDTO signupDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.signup(signupDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response){

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

    @PostMapping("/logout")
    public ResponseEntity<LogoutDTO> logout(HttpServletRequest request, HttpServletResponse response){
        String refreshToken = getRefreshTokenFromCookie(request);

        sessionService.deleteSessionByToken(refreshToken);

        Cookie accessToken = new Cookie("accessToken", null);
        accessToken.setMaxAge(0);
        accessToken.setHttpOnly(true);
        response.addCookie(accessToken);

        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setMaxAge(0);
        refreshTokenCookie.setHttpOnly(true);
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(
                LogoutDTO.builder()
                        .message("Logged out successfully")
                        .success(true)
                        .build()
        );

    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refreshToken(HttpServletRequest request){
        LoginResponseDTO loginResponseDTO = authService.refreshToken(getRefreshTokenFromCookie(request));
        return ResponseEntity.ok(loginResponseDTO);

    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/onboard-new-driver/{userId}")
    public ResponseEntity<DriverDTO> onboardNewDriver(@PathVariable Long userId,
                                               @RequestBody OnboardDriverDTO onboardDriverDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.onboardNewDriver(userId, onboardDriverDTO));
    }

    private String getRefreshTokenFromCookie(HttpServletRequest request){
        return  Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh Token not found in cookies"));
    }
}