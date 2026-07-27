package com.app.bs.booking_system.modules.seats;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.bs.booking_system.modules.screens.Screen;
import com.app.bs.booking_system.modules.screens.ScreenRepository;
import com.app.bs.booking_system.modules.seats.dto.CreateSeatsDTO;

@Service
public class SeatService {
  private final SeatRepository seatRepository;
  private final ScreenRepository screenRepository;

  public SeatService(SeatRepository seatRepository, ScreenRepository screenRepository) {
    this.seatRepository = seatRepository;
    this.screenRepository = screenRepository;
  }

  public List<Seat> createSeats(CreateSeatsDTO seatsDTO) {
    Screen screen = screenRepository.findById(seatsDTO.getScreen_id())
      .orElseThrow(() -> new RuntimeException("Screen not found"));
    List<Seat> seats = new ArrayList<>();
    for(char i = 'A';i <= 'J';i++) {
      for(int j = 1;j <= 10;j++) {
        StringBuilder seatName = new StringBuilder("");
        seatName.append(i);
        seatName.append('-');
        seatName.append(j);

        Seat seat = Seat.builder()
          .seatName(seatName.toString())
          .screen(screen)
          .build();
        seats.add(seat);
      }
    }
    seatRepository.saveAll(seats);
    return seatRepository.findAllByScreen(screen);
  } 

}
