package com.app.bs.booking_system.modules.show_seats;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("show_seat")
public class ShowSeatController {
  private final ShowSeatService showSeatService;

  @GetMapping("/")
  public List<ShowSeat> getShowSeats(UUID showId) {
    return showSeatService.getShowSeats(showId);
  }
}
