package com.app.bs.booking_system.modules.screens;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.app.bs.booking_system.exceptions.APIException;
import com.app.bs.booking_system.exceptions.ResourceNotFoundException;
import com.app.bs.booking_system.exceptions.UnauthorizedException;
import com.app.bs.booking_system.modules.screens.DTO.CreateScreenRequestDTO;
import com.app.bs.booking_system.modules.seats.SeatService;
import com.app.bs.booking_system.modules.theater.Theater;
import com.app.bs.booking_system.modules.theater.TheaterRepository;

@Service
public class ScreenService {
  private final SeatService seatService;
  private final ScreenRepository screenRepository;
  private final TheaterRepository theaterRepository;

  public ScreenService(ScreenRepository screenRepository, TheaterRepository theaterRepository,
      SeatService seatService) {
    this.screenRepository = screenRepository;
    this.theaterRepository = theaterRepository;
    this.seatService = seatService;
  }

  public Screen createScreen(CreateScreenRequestDTO screen, UUID authenticatedUserId) {
    Theater theater = theaterRepository.findById(screen.getTheater_id())
        .orElseThrow(() -> new ResourceNotFoundException("Theater not found"));
    if(!theater.getOwner().getId().equals(authenticatedUserId)) {
      throw new UnauthorizedException("Not authorized for this theater");
    }
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
