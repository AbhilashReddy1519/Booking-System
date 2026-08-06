package com.app.bs.booking_system.modules.bookings.workers;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.app.bs.booking_system.modules.bookings.BookingService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookingExpirationWorker {
  
  private final BookingService bookingService;

  // @Scheduled(cron = "0 * * * * *") // every minute
  @Scheduled(fixedDelay = 30_000) // every 30 seconds
  public void expireBooking() {
    bookingService.expireBooking();
  }
}
