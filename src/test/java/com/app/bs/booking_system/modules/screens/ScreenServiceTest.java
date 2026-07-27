package com.app.bs.booking_system.modules.screens;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.app.bs.booking_system.modules.screens.DTO.CreateScreenRequestDTO;
import com.app.bs.booking_system.modules.seats.SeatRepository;
import com.app.bs.booking_system.modules.theater.Theater;
import com.app.bs.booking_system.modules.theater.TheaterRepository;

class ScreenServiceTest {

  @Test
  void createScreenCreatesDefaultSeats() {
    ScreenRepository screenRepository = mock(ScreenRepository.class);
    TheaterRepository theaterRepository = mock(TheaterRepository.class);
    SeatRepository seatRepository = mock(SeatRepository.class);

    ScreenService screenService = new ScreenService(screenRepository, theaterRepository, seatRepository);

    UUID theaterId = UUID.randomUUID();
    Theater theater = Theater.builder().id(theaterId).build();
    CreateScreenRequestDTO request = new CreateScreenRequestDTO();
    request.setName("Screen 1");
    request.setTheater_id(theaterId);

    when(theaterRepository.findById(theaterId)).thenReturn(Optional.of(theater));
    when(screenRepository.save(any(Screen.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Screen createdScreen = screenService.createScreen(request);

    assertNotNull(createdScreen);
    verify(seatRepository).saveAll(any());
  }
}
