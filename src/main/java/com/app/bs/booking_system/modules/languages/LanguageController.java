package com.app.bs.booking_system.modules.languages;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.bs.booking_system.modules.languages.dto.CreateLanguageDTO;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/language")
public class LanguageController {
  private final LanguageService languageService;
  
  public LanguageController(LanguageService languageService) {
    this.languageService = languageService;
  }

  @PostMapping("")
  public Language createLanguage(@Valid @RequestBody CreateLanguageDTO language) {
    return languageService.createLanguage(language);
  }
  
  @GetMapping("")
  public List<Language> getMethodName() {
    return languageService.getLanguages();
  }
  
}
