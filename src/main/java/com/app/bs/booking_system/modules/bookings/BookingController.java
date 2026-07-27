package com.app.bs.booking_system.modules.bookings;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.bs.booking_system.modules.bookings.dto.CreateBookingDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/booking")
public class BookingController {
  private final BookingService bookingService;

  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @PostMapping("")
  public Booking createBooking(@RequestBody CreateBookingDTO createBookingDTO) {
    return bookingService.createBooking(createBookingDTO);
  }

  @GetMapping("")
  public List<Booking> getBookings() {
    return bookingService.getBookings();
  }
  
  
}
