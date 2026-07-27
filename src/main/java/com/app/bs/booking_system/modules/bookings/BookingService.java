package com.app.bs.booking_system.modules.bookings;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.app.bs.booking_system.modules.booking_seat.BookingSeat;
import com.app.bs.booking_system.modules.booking_seat.BookingSeatRepository;
import com.app.bs.booking_system.modules.bookings.dto.CreateBookingDTO;
import com.app.bs.booking_system.modules.seats.Seat;
import com.app.bs.booking_system.modules.seats.SeatRepository;
import com.app.bs.booking_system.modules.show_seats.ShowSeat;
import com.app.bs.booking_system.modules.show_seats.ShowSeatRepository;
import com.app.bs.booking_system.modules.show_seats.ShowSeatStatus;
import com.app.bs.booking_system.modules.shows.Show;
import com.app.bs.booking_system.modules.shows.ShowRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingService {
  private final SeatRepository seatRepository;
  private final BookingRepository bookingRepository;
  private final ShowRepository showRepository;
  private final BookingSeatRepository bookingSeatRepository;
  private final ShowSeatRepository showSeatRepository;

  public BookingService(
      BookingRepository bookingRepository,
      ShowRepository showRepository,
      BookingSeatRepository bookingSeatRepository,
      SeatRepository seatRepository,
      ShowSeatRepository showSeatRepository) {
    this.bookingRepository = bookingRepository;
    this.showRepository = showRepository;
    this.bookingSeatRepository = bookingSeatRepository;
    this.seatRepository = seatRepository;
    this.showSeatRepository = showSeatRepository;
  }

  @Transactional
  public Booking createBooking(CreateBookingDTO createBookingDTO) {
    Show show = showRepository.findById(createBookingDTO.getShow_id())
        .orElseThrow(() -> new RuntimeException("Show not found"));

    Booking booking = Booking.builder()
        .show(show)
        .status(BookingStatus.PROCESSING)
        .build();

    booking = bookingRepository.save(booking);

    List<BookingSeat> bookingSeats = new ArrayList<>();
    for (UUID seatId : createBookingDTO.getSeat_ids()) {
      Seat seat = seatRepository.findById(seatId)
          .orElseThrow(() -> new RuntimeException("Seat is not found"));

      reserveSeat(show, seat);

      BookingSeat bookingSeat = BookingSeat.builder()
          .seat(seat)
          .booking(booking)
          .build();

      bookingSeats.add(bookingSeat);
    }

    bookingSeatRepository.saveAll(bookingSeats);

    // todo: Payment Service
    try {
      System.out.println("Processing payment...");
      Thread.sleep(1 * 30 * 1000);
      System.out.println("Payment completed.");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Payment process interrupted", e);
    }

    booking.setStatus(BookingStatus.BOOKED);
    booking.setBookingSeats(bookingSeats);
    return bookingRepository.save(booking);
  }

  @Transactional
  public void reserveSeat(Show show, Seat seat) {
    Optional<ShowSeat> existingSeat = showSeatRepository.findByShowIdAndSeatIdForUpdate(show.getId(), seat.getId());

    if (existingSeat.isPresent()) {
      ShowSeat showSeat = existingSeat.get();
      if (showSeat.getStatus() == ShowSeatStatus.BOOKED || showSeat.getStatus() == ShowSeatStatus.RESERVED) {
        throw new IllegalStateException("Seat already booked for the selected show");
      }
      showSeat.setStatus(ShowSeatStatus.RESERVED);
      showSeatRepository.save(showSeat);
      return;
    }

    ShowSeat newShowSeat = ShowSeat.builder()
        .show(show)
        .seat(seat)
        .status(ShowSeatStatus.RESERVED)
        .build();

    try {
      showSeatRepository.saveAndFlush(newShowSeat);
    } catch (DataIntegrityViolationException exception) {
      ShowSeat lockedSeat = showSeatRepository.findByShowIdAndSeatIdForUpdate(show.getId(), seat.getId())
          .orElseThrow(() -> new IllegalStateException("Seat reservation failed"));
      if (lockedSeat.getStatus() == ShowSeatStatus.BOOKED) {
        throw new IllegalStateException("Seat already booked for the selected show");
      }
      lockedSeat.setStatus(ShowSeatStatus.BOOKED);
      showSeatRepository.save(lockedSeat);
    }
  }

  public List<Booking> getBookings() {
    return bookingRepository.findAll();
  }
}
