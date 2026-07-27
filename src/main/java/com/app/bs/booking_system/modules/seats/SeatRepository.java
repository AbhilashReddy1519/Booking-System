package com.app.bs.booking_system.modules.seats;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.app.bs.booking_system.modules.screens.Screen;


public interface SeatRepository extends JpaRepository<Seat, UUID>{
  List<Seat> findAllByScreen(Screen screen);
}
