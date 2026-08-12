package com.app.bs.booking_system.modules.show_seats;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.bs.booking_system.exceptions.ResourceNotFoundException;
import com.app.bs.booking_system.modules.screens.Screen;
import com.app.bs.booking_system.modules.seats.Seat;
import com.app.bs.booking_system.modules.seats.SeatCategory;
import com.app.bs.booking_system.modules.seats.SeatRepository;
import com.app.bs.booking_system.modules.shows.Show;
import com.app.bs.booking_system.modules.shows.ShowRepository;

import jakarta.validation.ValidationException;

@Service
public class ShowSeatService {
  private final ShowSeatRepository showSeatRepository;
  private final SeatRepository seatRepository;
  private final ShowRepository showRepository;

  public ShowSeatService(ShowSeatRepository showSeatRepository, SeatRepository seatRepository,
      ShowRepository showRepository) {
    this.showSeatRepository = showSeatRepository;
    this.seatRepository = seatRepository;
    this.showRepository = showRepository;
  }

  public void createShowSeats(Show show, Screen screen, Map<SeatCategory, BigDecimal> categoryPrices) {
    List<Seat> seats = seatRepository.findAllByScreen(screen);
    if (seats.isEmpty()) {
      throw new ResourceNotFoundException("No seats found for screen");
    }
     // validate every category actually present on this screen has a price — fail fast, before creating anything
    Set<SeatCategory> categoriesOnScreen = seats.stream().map(Seat::getSeatCategory).collect(Collectors.toSet());
    for (SeatCategory cat : categoriesOnScreen) {
        if (!categoryPrices.containsKey(cat)) {
            throw new ValidationException("Missing price for seat category: " + cat);
        }
    }

    List<ShowSeat> showSeats = seats.stream().map(seat -> {
      BigDecimal price = categoryPrices.get(seat.getSeatCategory());
      return ShowSeat.builder()
          .seat(seat)
          .show(show)
          .price(price)
          .status(ShowSeatStatus.AVAILABLE)
          .build();
    }).collect(Collectors.toList());
    showSeatRepository.saveAll(showSeats);
  }

  public List<ShowSeat> getShowSeats(UUID showId) {
    Show show = showRepository.findById(showId)
        .orElseThrow(() -> new ResourceNotFoundException("show not found"));
    return showSeatRepository.findByShow(show);
  }
}
