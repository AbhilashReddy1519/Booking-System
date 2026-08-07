package com.app.bs.booking_system.modules.screens;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.bs.booking_system.modules.screens.DTO.CreateScreenRequestDTO;
import com.app.bs.booking_system.modules.seats.SeatService;
import com.app.bs.booking_system.modules.theater.Theater;
import com.app.bs.booking_system.modules.theater.TheaterRepository;

@Service
public class ScreenService {
  private final SeatService seatService;
  private final ScreenRepository screenRepository;
  private final TheaterRepository theaterRepository;

  public ScreenService(ScreenRepository screenRepository, TheaterRepository theaterRepository, SeatService seatService) {
    this.screenRepository = screenRepository;
    this.theaterRepository = theaterRepository;
    this.seatService = seatService;
  }

  public Screen createScreen(CreateScreenRequestDTO screen) {
    Theater theater = theaterRepository.findById(screen.getTheater_id())
      .orElseThrow(() -> new RuntimeException("Theater not found"));

    Screen newScreen = Screen.builder()
      .name(screen.getName())
      .theater(theater)
      .build();

    Screen savedScreen = screenRepository.save(newScreen);
    seatService.createSeats(savedScreen);
    return savedScreen;
  }

  public List<Screen> getScreens() {
    return screenRepository.findAll();
  }
}
