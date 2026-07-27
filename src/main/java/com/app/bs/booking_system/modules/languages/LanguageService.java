package com.app.bs.booking_system.modules.languages;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.bs.booking_system.modules.languages.dto.CreateLanguageDTO;
import com.app.bs.booking_system.modules.movies.MovieRepository;

@Service
public class LanguageService {
  private final LanguageRepository languageRepository;
  public LanguageService(LanguageRepository languageRepository, MovieRepository movieRepository) {
    this.languageRepository = languageRepository;
  }

  public Language createLanguage(CreateLanguageDTO language) {
    Language newLanguage = Language.builder()
      .language(language.getLanguage())
      .build();   

    return languageRepository.save(newLanguage);
  }

  public List<Language> getLanguages() {
    return languageRepository.findAll();
  }
}
