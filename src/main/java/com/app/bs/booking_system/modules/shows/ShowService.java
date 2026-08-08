package com.app.bs.booking_system.modules.shows;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.bs.booking_system.exceptions.ResourceNotFoundException;
import com.app.bs.booking_system.modules.languages.Language;
import com.app.bs.booking_system.modules.languages.LanguageRepository;
import com.app.bs.booking_system.modules.movies.Movie;
import com.app.bs.booking_system.modules.movies.MovieRepository;
import com.app.bs.booking_system.modules.screens.Screen;
import com.app.bs.booking_system.modules.screens.ScreenRepository;
import com.app.bs.booking_system.modules.seats.SeatRepository;
import com.app.bs.booking_system.modules.show_seats.ShowSeatRepository;
import com.app.bs.booking_system.modules.show_seats.ShowSeatService;
import com.app.bs.booking_system.modules.shows.dto.CreateShowDTO;

@Service
public class ShowService {
  private final ShowRepository showRepository;
  private final MovieRepository movieRepository;
  private final ScreenRepository screenRepository;
  private final LanguageRepository languageRepository;
  private final ShowSeatService showSeatService;

  public ShowService(
      ShowRepository showRepository,
      MovieRepository movieRepository,
      ScreenRepository screenRepository,
      LanguageRepository languageRepository,
      SeatRepository seatRepository,
      ShowSeatRepository showSeatRepository, ShowSeatService showSeatService) {
    this.showRepository = showRepository;
    this.movieRepository = movieRepository;
    this.screenRepository = screenRepository;
    this.languageRepository = languageRepository;
    this.showSeatService = showSeatService;
  }

  public Show createShow(CreateShowDTO showDTO) {
    Movie movie = movieRepository.findById(showDTO.getMovieId())
        .orElseThrow(() -> new ResourceNotFoundException("Movie is not found"));
    Screen screen = screenRepository.findById(showDTO.getScreenId())
        .orElseThrow(() -> new ResourceNotFoundException("Screen is not found"));
    Language language = languageRepository.findById(showDTO.getLanguageId())
        .orElseThrow(() -> new ResourceNotFoundException("Language is not found"));

    Show show = Show.builder()
        .startDateTime(showDTO.getStartDateTime())
        .endDateTime(showDTO.getEndDateTime())
        .movie(movie)
        .screen(screen)
        .language(language)
        .build();

    Show savedShow = showRepository.save(show);
    // Create showSeats
    showSeatService.createShowSeats(savedShow, screen);
    return savedShow;
  }

  public List<Show> getShows() {
    return showRepository.findAll();
  }
}
