package com.akhil.uber_backend.uber_ride.services.impl;

import com.akhil.uber_backend.uber_ride.dto.DriverDTO;
import com.akhil.uber_backend.uber_ride.dto.SignupDTO;
import com.akhil.uber_backend.uber_ride.dto.UserDTO;
import com.akhil.uber_backend.uber_ride.enums.Role;
import com.akhil.uber_backend.uber_ride.exceptions.RuntimeConflictException;
import com.akhil.uber_backend.uber_ride.models.User;
import com.akhil.uber_backend.uber_ride.repositories.UserRepository;
import com.akhil.uber_backend.uber_ride.services.AuthService;
import com.akhil.uber_backend.uber_ride.services.RiderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService;

    @Override
    public String login(String email, String password) {
        return "";
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

        User savedUser = userRepository.save(user);

        riderService.createNewRider(user);
        // TODO all wallet related service

        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public DriverDTO onboardNewDriver(Long userId) {
        return null;
    }
}
