package com.akhil.uber_backend.uber_ride.services;

import com.akhil.uber_backend.uber_ride.models.User;

public interface SessionService {

    void generateNewSession(User user, String refreshToken);

    void validateSession(String refreshToken);

    void deleteSessionByToken(String refreshToken);
}