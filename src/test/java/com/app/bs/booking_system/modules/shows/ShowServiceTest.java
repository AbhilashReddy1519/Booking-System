package com.app.bs.booking_system.modules.shows;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.app.bs.booking_system.modules.languages.Language;
import com.app.bs.booking_system.modules.languages.LanguageRepository;
import com.app.bs.booking_system.modules.movies.Movie;
import com.app.bs.booking_system.modules.movies.MovieRepository;
import com.app.bs.booking_system.modules.screens.Screen;
import com.app.bs.booking_system.modules.screens.ScreenRepository;
import com.app.bs.booking_system.modules.seats.Seat;
import com.app.bs.booking_system.modules.seats.SeatRepository;
import com.app.bs.booking_system.modules.show_seats.ShowSeat;
import com.app.bs.booking_system.modules.show_seats.ShowSeatRepository;
import com.app.bs.booking_system.modules.show_seats.ShowSeatStatus;
import com.app.bs.booking_system.modules.shows.dto.CreateShowDTO;

class ShowServiceTest {

  @Test
  void createShowPopulatesShowSeatsForAllSeatsOnTheScreen() {
    ShowRepository showRepository = mock(ShowRepository.class);
    MovieRepository movieRepository = mock(MovieRepository.class);
    ScreenRepository screenRepository = mock(ScreenRepository.class);
    LanguageRepository languageRepository = mock(LanguageRepository.class);
    SeatRepository seatRepository = mock(SeatRepository.class);
    ShowSeatRepository showSeatRepository = mock(ShowSeatRepository.class);

    ShowService showService = new ShowService(
        showRepository,
        movieRepository,
        screenRepository,
        languageRepository,
        seatRepository,
        showSeatRepository
    );

    UUID movieId = UUID.randomUUID();
    UUID screenId = UUID.randomUUID();
    UUID languageId = UUID.randomUUID();

    Movie movie = Movie.builder().id(movieId).build();
    Screen screen = Screen.builder().id(screenId).build();
    Language language = Language.builder().id(languageId).build();
    Seat seat1 = Seat.builder().id(UUID.randomUUID()).seatName("A1").screen(screen).build();
    Seat seat2 = Seat.builder().id(UUID.randomUUID()).seatName("A2").screen(screen).build();

    when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
    when(screenRepository.findById(screenId)).thenReturn(Optional.of(screen));
    when(languageRepository.findById(languageId)).thenReturn(Optional.of(language));
    when(seatRepository.findAllByScreen(screen)).thenReturn(List.of(seat1, seat2));
    when(showRepository.save(any(Show.class))).thenAnswer(invocation -> invocation.getArgument(0));
    when(showSeatRepository.saveAll(any(List.class))).thenAnswer(invocation -> invocation.getArgument(0));

    CreateShowDTO dto = new CreateShowDTO();
    dto.setStartDateTime(LocalDateTime.now());
    dto.setEndDateTime(LocalDateTime.now().plusHours(2));
    dto.setMovieId(movieId);
    dto.setScreenId(screenId);
    dto.setLanguageId(languageId);

    Show createdShow = showService.createShow(dto);

    assertNotNull(createdShow);
    verify(showSeatRepository).saveAll(any(List.class));
  }
}
