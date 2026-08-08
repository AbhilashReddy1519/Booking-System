package com.app.bs.booking_system.modules.payment;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import jakarta.persistence.LockModeType;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);
}
