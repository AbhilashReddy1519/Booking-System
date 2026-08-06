package com.app.bs.booking_system.modules.booking_seat;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.bs.booking_system.modules.bookings.Booking;


public interface BookingSeatRepository extends JpaRepository<BookingSeat, UUID>{
  List<BookingSeat> findByBooking(Booking booking);
}
