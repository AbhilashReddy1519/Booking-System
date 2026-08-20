package com.app.bs.booking_system.modules.screens;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.bs.booking_system.modules.screens.DTO.CreateScreenRequestDTO;
// import com.app.bs.booking_system.modules.screens.ScreenService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/screen")
public class ScreenController {
  private final ScreenService screenService;

  public ScreenController(ScreenService screenService) {
    this.screenService = screenService;
  }


  @PostMapping("")
  public Screen createScreen(@Valid @RequestBody CreateScreenRequestDTO screen, @AuthenticationPrincipal UUID userId) {
    return screenService.createScreen(screen, userId);  
  }

  @GetMapping("")
  public List<Screen> getScreens() {
    return screenService.getScreens();
  }
  
  
}
