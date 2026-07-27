package com.app.bs.booking_system.modules.languages;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.bs.booking_system.modules.languages.dto.CreateLanguageDTO;
import com.app.bs.booking_system.modules.movies.Movie;
import com.app.bs.booking_system.modules.movies.MovieRepository;

@Service
public class LanguageService {
  private final LanguageRepository languageRepository;
  private final MovieRepository movieRepository;

  public LanguageService(LanguageRepository languageRepository, MovieRepository movieRepository) {
    this.languageRepository = languageRepository;
    this.movieRepository = movieRepository;
  }

  public Language createLanguage(CreateLanguageDTO language) {
    Movie movie = movieRepository.findById(language.getMovie_id())
      .orElseThrow(() -> new RuntimeException("Movie not found"));

    Language newLanguage = Language.builder()
      .language(language.getLanguage())
      .movie(movie) 
      .build();   

    return languageRepository.save(newLanguage);
  }

  public List<Language> getLanguages() {
    return languageRepository.findAll();
  }
}
