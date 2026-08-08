package com.app.bs.booking_system.modules.seats;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.bs.booking_system.modules.seats.dto.CreateSeatsDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/seats")
public class SeatController {
  private final SeatService seatService;

  public SeatController(SeatService seatService) {
    this.seatService = seatService;
  }

  @PostMapping("")
  public List<Seat> createSeats(@Valid @RequestBody CreateSeatsDTO seatDTO) {
    return seatService.createSeats(seatDTO);
  }

  @GetMapping("")
  public List<Seat> createSeats() {
    return seatService.getSeats();
  }
}
