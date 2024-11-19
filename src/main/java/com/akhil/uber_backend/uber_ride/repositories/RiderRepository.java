package com.akhil.uber_backend.uber_ride.repositories;

import com.akhil.uber_backend.uber_ride.models.Rider;
import com.akhil.uber_backend.uber_ride.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Long> {
    Optional<Rider> findByUser(User user);
}