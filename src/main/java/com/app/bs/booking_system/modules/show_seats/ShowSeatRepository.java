package com.app.bs.booking_system.modules.show_seats;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import com.app.bs.booking_system.modules.shows.Show;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({ @QueryHint(name = "jakarta.persistence.lock.timeout", value = "0") })
    @Query("""
            SELECT ss
            FROM ShowSeat ss
            WHERE ss.show = :show
            AND ss.seat.id IN :seatIds
            ORDER BY ss.seat.id
            """)
    List<ShowSeat> findAllByShowIdAndSeatIdForUpdate(@Param("show") Show show, @Param("seatIds") List<UUID> seatIds);
    
    @Query("select ss from ShowSeat ss where ss.show.id = :showId and ss.seat.id = :seatId")
    Optional<ShowSeat> findByShowIdAndSeatIdForUpdate(@Param("showId") UUID showId, @Param("seatId") UUID seatId);
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT ss
            FROM ShowSeat ss
            WHERE ss.show = :show
            AND ss.seat.id IN :seatIds
            """)
    List<ShowSeat> findAllByShowAndSeatIds(
            @Param("show") Show show,
            @Param("seatIds") List<UUID> seatIds);
}
