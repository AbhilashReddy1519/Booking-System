package com.app.bs.booking_system.modules.screens;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.bs.booking_system.modules.screens.DTO.CreateScreenRequestDTO;
import com.app.bs.booking_system.modules.seats.Seat;
import com.app.bs.booking_system.modules.seats.SeatRepository;
import com.app.bs.booking_system.modules.theater.Theater;
import com.app.bs.booking_system.modules.theater.TheaterRepository;

@Service
public class ScreenService {
  private final ScreenRepository screenRepository;
  private final TheaterRepository theaterRepository;
  private final SeatRepository seatRepository;

  public ScreenService(ScreenRepository screenRepository, TheaterRepository theaterRepository, SeatRepository seatRepository) {
    this.screenRepository = screenRepository;
    this.theaterRepository = theaterRepository;
    this.seatRepository = seatRepository;
  }

  public Screen createScreen(CreateScreenRequestDTO screen) {
    Theater theater = theaterRepository.findById(screen.getTheater_id())
      .orElseThrow(() -> new RuntimeException("Theater not found"));

    Screen newScreen = Screen.builder()
      .name(screen.getName())
      .theater(theater)
      .build();

    Screen savedScreen = screenRepository.save(newScreen);

    List<Seat> seats = new ArrayList<>();
    for (char row = 'A'; row <= 'J'; row++) {
      for (int number = 1; number <= 10; number++) {
        seats.add(Seat.builder()
            .seatName(row + "-" + number)
            .screen(savedScreen)
            .build());
      }
    }

    seatRepository.saveAll(seats);
    return savedScreen;
  }

  public List<Screen> getScreens() {
    return screenRepository.findAll();
  }
}
