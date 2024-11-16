package com.akhil.uber_backend.uber_ride.repositories;

import com.akhil.uber_backend.uber_ride.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
