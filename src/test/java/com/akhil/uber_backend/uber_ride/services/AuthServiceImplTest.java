package com.akhil.uber_backend.uber_ride.services;

import com.akhil.uber_backend.uber_ride.dto.LoginResponseDTO;
import com.akhil.uber_backend.uber_ride.dto.SignupDTO;
import com.akhil.uber_backend.uber_ride.dto.UserDTO;
import com.akhil.uber_backend.uber_ride.enums.Role;
import com.akhil.uber_backend.uber_ride.models.User;
import com.akhil.uber_backend.uber_ride.repositories.UserRepository;
import com.akhil.uber_backend.uber_ride.repositories.VehicleRepository;
import com.akhil.uber_backend.uber_ride.securities.JwtService;
import com.akhil.uber_backend.uber_ride.services.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private WalletServiceImpl walletService;

    @Mock
    private RiderServiceImpl riderService;

    @Mock
    private DriverServiceImpl driverService;

    @Spy
    private ModelMapper modelMapper;

    @Spy
    private PasswordEncoder passwordEncoder;

    @Mock
    private SessionServiceImpl sessionService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    private User user;
    private SignupDTO signupDTO;
    private LoginResponseDTO loginResponseDTO;

    @BeforeEach
    void setUp(){
        user = new User();
        user.setId(1L);
        user.setName("Joe");
        user.setEmail("joe@email.com");
        user.setPassword("password");
        user.setRoles(Set.of(Role.RIDER));
    }

    @Test
    void testLogin_whenSuccess(){

        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(jwtService.generateAccessToken(any(User.class))).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");

        // Act
        loginResponseDTO = authService.login(user.getEmail(), user.getPassword());

        // Assert
        assertThat(loginResponseDTO.getAccessToken()).isEqualTo("accessToken");
        assertThat(loginResponseDTO.getRefreshToken()).isEqualTo("refreshToken");
    }

    @Test
    void testSignup_whenSuccess(){
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        signupDTO = new SignupDTO();
        signupDTO.setName("Joe");
        signupDTO.setEmail("joe@email.com");
        signupDTO.setPassword("password");
        UserDTO userDTO = authService.signup(signupDTO);

        // Assert
        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getEmail()).isEqualTo(signupDTO.getEmail());

        verify(riderService).createNewRider(any(User.class));
        verify(walletService).createNewWallet(any(User.class));
    }

}