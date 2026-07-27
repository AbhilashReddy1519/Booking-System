package com.app.bs.booking_system.modules.shows;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

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

@Service
public class ShowService {
  private final ShowRepository showRepository;
  private final MovieRepository movieRepository;
  private final ScreenRepository screenRepository;
  private final LanguageRepository languageRepository;
  private final SeatRepository seatRepository;
  private final ShowSeatRepository showSeatRepository;

  public ShowService(
      ShowRepository showRepository,
      MovieRepository movieRepository,
      ScreenRepository screenRepository,
      LanguageRepository languageRepository,
      SeatRepository seatRepository,
      ShowSeatRepository showSeatRepository) {
    this.showRepository = showRepository;
    this.movieRepository = movieRepository;
    this.screenRepository = screenRepository;
    this.languageRepository = languageRepository;
    this.seatRepository = seatRepository;
    this.showSeatRepository = showSeatRepository;
  }

  public Show createShow(CreateShowDTO showDTO) {
    Movie movie = movieRepository.findById(showDTO.getMovieId())
      .orElseThrow(() -> new RuntimeException("Movie is not found"));
    Screen screen = screenRepository.findById(showDTO.getScreenId())
      .orElseThrow(() -> new RuntimeException("Screen is not found"));
    Language language = languageRepository.findById(showDTO.getLanguageId())
      .orElseThrow(() -> new RuntimeException("Language is not found"));

    Show show = Show.builder()
      .startDateTime(showDTO.getStartDateTime())
      .endDateTime(showDTO.getEndDateTime())
      .movie(movie)
      .screen(screen)
      .language(language)
      .build();

    Show savedShow = showRepository.save(show);

    List<Seat> seats = seatRepository.findAllByScreen(screen);
    List<ShowSeat> showSeats = new ArrayList<>();
    for (Seat seat : seats) {
      showSeats.add(ShowSeat.builder()
          .show(savedShow)
          .seat(seat)
          .status(ShowSeatStatus.AVAILABLE)
          .build());
    }

    if (!showSeats.isEmpty()) {
      showSeatRepository.saveAll(showSeats);
    }

    return savedShow;
  }

  public List<Show> getShows() {
    return showRepository.findAll();
  }
}
