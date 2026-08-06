package com.app.bs.booking_system.modules.bookings;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.app.bs.booking_system.modules.booking_seat.BookingSeat;
import com.app.bs.booking_system.modules.booking_seat.BookingSeatRepository;
import com.app.bs.booking_system.modules.bookings.dto.CreateBookingDTO;
import com.app.bs.booking_system.modules.show_seats.ShowSeat;
import com.app.bs.booking_system.modules.show_seats.ShowSeatRepository;
import com.app.bs.booking_system.modules.show_seats.ShowSeatStatus;
import com.app.bs.booking_system.modules.shows.Show;
import com.app.bs.booking_system.modules.shows.ShowRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingService {
  private final BookingSeatRepository bookingSeatRepository;
  private final BookingRepository bookingRepository;
  private final ShowRepository showRepository;
  private final ShowSeatRepository showSeatRepository;

  public BookingService(
      BookingRepository bookingRepository,
      ShowRepository showRepository,
      ShowSeatRepository showSeatRepository, BookingSeatRepository bookingSeatRepository) {
    this.bookingRepository = bookingRepository;
    this.showRepository = showRepository;
    this.showSeatRepository = showSeatRepository;
    this.bookingSeatRepository = bookingSeatRepository;
  }

  @Transactional
  public Booking createBooking(CreateBookingDTO createBookingDTO) {
    Show show = showRepository.findById(createBookingDTO.getShow_id())
        .orElseThrow(() -> new RuntimeException("Show not found"));

    // Sort to acquire locks consistently
    List<UUID> seatIds = new ArrayList<>(createBookingDTO.getSeat_ids());
    if (seatIds.contains(null)) {
      throw new IllegalArgumentException("Seat IDs cannot contain null.");
    }
    seatIds.sort(UUID::compareTo);
    List<ShowSeat> showSeats = showSeatRepository.findAllByShowIdAndSeatIdForUpdate(show, seatIds);

    Booking booking = Booking.builder()
        .show(show)
        .status(BookingStatus.PROCESSING)
        .build();

    bookingRepository.save(booking);

    List<BookingSeat> bookingSeats = new ArrayList<>();
    if (showSeats.size() != createBookingDTO.getSeat_ids().size()) {
      throw new IllegalStateException("Invalid seat selection.");
    }
    for (ShowSeat showSeat : showSeats) {
      if (showSeat.getStatus() != ShowSeatStatus.AVAILABLE) {
        throw new IllegalStateException("One or more seats are already reserved.");
      }
      showSeat.setStatus(ShowSeatStatus.RESERVED);

      BookingSeat bookingSeat = BookingSeat.builder()
          .booking(booking)
          .seat(showSeat.getSeat())
          .build();

      bookingSeats.add(bookingSeat);
    }

    bookingSeatRepository.saveAll(bookingSeats);
    booking.setBookingSeats(bookingSeats);
    booking.setExpiresAt(LocalDateTime.now().plusMinutes(5));
    return booking;
  }

  @Transactional
  public void expireBooking() {
    List<Booking> pendingBookings = bookingRepository.findAllByStatusAndExpiresAtBefore(BookingStatus.PROCESSING,
        LocalDateTime.now());
    for (Booking pendingBooking : pendingBookings) {
      pendingBooking.setStatus(BookingStatus.EXPIRED);
      List<UUID> seatIds = pendingBooking.getBookingSeats()
          .stream()
          .map(bookingSeat -> bookingSeat.getSeat().getId())
          .toList();
      List<ShowSeat> showSeats = showSeatRepository.findAllByShowAndSeatIds(pendingBooking.getShow(), seatIds);
      showSeats.forEach(showSeat -> showSeat.setStatus(ShowSeatStatus.AVAILABLE));
    }

  }

  public List<Booking> getBookings() {
    return bookingRepository.findAll();
  }
}
