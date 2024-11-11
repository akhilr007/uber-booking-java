package com.akhil.uber_backend.uber_ride.repositories;

import com.akhil.uber_backend.uber_ride.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}