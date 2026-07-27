package com.app.bs.booking_system.modules.bookings;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, UUID>{
  
}
