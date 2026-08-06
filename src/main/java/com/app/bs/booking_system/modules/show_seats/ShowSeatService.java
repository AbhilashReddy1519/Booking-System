package com.app.bs.booking_system.modules.show_seats;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.bs.booking_system.modules.screens.Screen;
import com.app.bs.booking_system.modules.seats.Seat;
import com.app.bs.booking_system.modules.seats.SeatRepository;
import com.app.bs.booking_system.modules.shows.Show;

@Service
public class ShowSeatService {
  private final ShowSeatRepository showSeatRepository;
  private final SeatRepository seatRepository;
  public ShowSeatService(ShowSeatRepository showSeatRepository, SeatRepository seatRepository) {
    this.showSeatRepository = showSeatRepository;
    this.seatRepository = seatRepository;
  }
  
  public void createShowSeats(Show show, Screen screen) {
    List<Seat> seats = seatRepository.findAllByScreen(screen);
    
    if(seats.isEmpty()) {
      throw new RuntimeException("No seats found for screen");
    }

    List<ShowSeat> showSeats = new ArrayList<>();
    for(Seat seat: seats) {
      ShowSeat showSeat = ShowSeat.builder()
        .show(show)
        .seat(seat)
        .build();

      showSeats.add(showSeat);
    }
    if (!showSeats.isEmpty()) {
      showSeatRepository.saveAll(showSeats);
    }
  }
}
