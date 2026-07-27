package com.app.bs.booking_system.modules.theater;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TheaterService {
  private final TheaterRepository theaterRepository;

  public TheaterService(TheaterRepository theaterRepository) {
    this.theaterRepository = theaterRepository;
  }

  public Theater createTheater(Theater theater) {
    return theaterRepository.save((theater));
  }

  public List<Theater> getTheaters() {
    return theaterRepository.findAll();
  }
}
