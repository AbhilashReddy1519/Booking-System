package com.app.bs.booking_system.modules.show_seats;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, UUID> {
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select ss from ShowSeat ss where ss.show.id = :showId and ss.seat.id = :seatId")
  Optional<ShowSeat> findByShowIdAndSeatIdForUpdate(@Param("showId") UUID showId, @Param("seatId") UUID seatId);

  @Query("select ss from ShowSeat ss where ss.show.id = :showId and ss.seat.id = :seatId")
  Optional<ShowSeat> findByShowIdAndSeatId(@Param("showId") UUID showId, @Param("seatId") UUID seatId);
}
