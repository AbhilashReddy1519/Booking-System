package com.app.bs.booking_system.modules.shows;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.bs.booking_system.modules.languages.Language;
import com.app.bs.booking_system.modules.languages.LanguageRepository;
import com.app.bs.booking_system.modules.movies.Movie;
import com.app.bs.booking_system.modules.movies.MovieRepository;
import com.app.bs.booking_system.modules.screens.Screen;
import com.app.bs.booking_system.modules.screens.ScreenRepository;
import com.app.bs.booking_system.modules.shows.dto.CreateShowDTO;

@Service
public class ShowService {
  private final ShowRepository showRepository;
  private final MovieRepository movieRepository;
  private final ScreenRepository screenRepository;
  private final LanguageRepository languageRepository;

  public ShowService(ShowRepository showRepository, MovieRepository movieRepository, ScreenRepository screenRepository, LanguageRepository languageRepository) {
    this.showRepository = showRepository;
    this.movieRepository = movieRepository;
    this.screenRepository = screenRepository;
    this.languageRepository = languageRepository;
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

    return showRepository.save(show);
  }

  public List<Show> getShows() {
    return showRepository.findAll();
  }
}
