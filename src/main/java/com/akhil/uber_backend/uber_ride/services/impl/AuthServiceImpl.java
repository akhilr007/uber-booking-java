package com.akhil.uber_backend.uber_ride.services.impl;

import com.akhil.uber_backend.uber_ride.dto.*;
import com.akhil.uber_backend.uber_ride.enums.Role;
import com.akhil.uber_backend.uber_ride.exceptions.ResourceNotFoundException;
import com.akhil.uber_backend.uber_ride.exceptions.RuntimeConflictException;
import com.akhil.uber_backend.uber_ride.models.Driver;
import com.akhil.uber_backend.uber_ride.models.User;
import com.akhil.uber_backend.uber_ride.models.Vehicle;
import com.akhil.uber_backend.uber_ride.repositories.UserRepository;
import com.akhil.uber_backend.uber_ride.repositories.VehicleRepository;
import com.akhil.uber_backend.uber_ride.securities.JwtService;
import com.akhil.uber_backend.uber_ride.services.AuthService;
import com.akhil.uber_backend.uber_ride.services.DriverService;
import com.akhil.uber_backend.uber_ride.services.RiderService;
import com.akhil.uber_backend.uber_ride.services.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final VehicleRepository vehicleRepository;

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public LoginResponseDTO login(String email, String password) {

        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);


        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public UserDTO signup(SignupDTO signupDTO) {

        Optional<User> existingUser = userRepository.findByEmail(signupDTO.getEmail());
        if(existingUser.isPresent()){
            throw new RuntimeConflictException("Cannot signup, user already exists with an email " + signupDTO.getEmail());
        }

        User user = modelMapper.map(signupDTO, User.class);
        user.setRoles(Set.of(Role.RIDER));
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));

        User savedUser = userRepository.save(user);

        riderService.createNewRider(savedUser);

        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public DriverDTO onboardNewDriver(Long userId, OnboardDriverDTO onboardDriverDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID" + userId));

        Vehicle vehicle = modelMapper.map(onboardDriverDTO.getVehicleDTO(), Vehicle.class);
        vehicleRepository.save(vehicle);

        if(user.getRoles().contains(Role.DRIVER)){
            throw new RuntimeException("User with ID " + userId + " is already a driver");
        }

        Driver createDriver = Driver.builder()
                .user(user)
                .rating(0.0)
                .available(true)
                .vehicle(vehicle)
                .build();

        user.getRoles().add(Role.DRIVER);
        userRepository.save(user);

        Driver savedDriver = this.driverService.createNewDriver(createDriver);
        return modelMapper.map(savedDriver, DriverDTO.class);
    }
}