package com.app.bs.booking_system.modules.theater;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam; 



@RestController
@RequestMapping("/theater")
public class TheaterController {
  private final TheaterService theaterService;

  public TheaterController(TheaterService theaterService) {
    this.theaterService = theaterService;
  }

  @PostMapping("")
  public Theater creathTheater(@RequestBody Theater theater) {
    return theaterService.createTheater(theater);
  }

  @GetMapping("")
  public List<Theater> getTheaters() {
    return theaterService.getTheaters();
  }
  
  
}
