package com.app.bs.booking_system.modules.bookings;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, UUID>{
  @Query("""
    SELECT b
    FROM Booking b
    WHERE b.status = :status
      AND b.expiresAt <= :now
  """)
  List<Booking> findExpiredBookings(@Param("status") BookingStatus status, @Param("now") LocalDateTime now);

  List<Booking> findAllByStatusAndExpiresAtBefore(BookingStatus status, LocalDateTime localDateTime);
}
