package com.app.bs.booking_system.modules.bookings;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.app.bs.booking_system.modules.booking_seat.BookingSeatRepository;
import com.app.bs.booking_system.modules.seats.Seat;
import com.app.bs.booking_system.modules.seats.SeatRepository;
import com.app.bs.booking_system.modules.show_seats.ShowSeat;
import com.app.bs.booking_system.modules.show_seats.ShowSeatRepository;
import com.app.bs.booking_system.modules.show_seats.ShowSeatStatus;
import com.app.bs.booking_system.modules.shows.Show;
import com.app.bs.booking_system.modules.shows.ShowRepository;

class BookingServiceTest {

  @Test
  void reserveSeatThrowsWhenSeatAlreadyBooked() {
    BookingRepository bookingRepository = mock(BookingRepository.class);
    ShowRepository showRepository = mock(ShowRepository.class);
    BookingSeatRepository bookingSeatRepository = mock(BookingSeatRepository.class);
    SeatRepository seatRepository = mock(SeatRepository.class);
    ShowSeatRepository showSeatRepository = mock(ShowSeatRepository.class);

    BookingService bookingService = new BookingService(
        bookingRepository,
        showRepository,
        bookingSeatRepository,
        seatRepository,
        showSeatRepository
    );

    Show show = Show.builder()
        .id(UUID.randomUUID())
        .startDateTime(LocalDateTime.now())
        .endDateTime(LocalDateTime.now().plusHours(2))
        .build();
    Seat seat = Seat.builder().id(UUID.randomUUID()).seatName("A1").build();
    ShowSeat showSeat = ShowSeat.builder()
        .show(show)
        .seat(seat)
        .status(ShowSeatStatus.BOOKED)
        .build();

    when(showSeatRepository.findByShowIdAndSeatIdForUpdate(show.getId(), seat.getId()))
        .thenReturn(Optional.of(showSeat));

    assertThrows(IllegalStateException.class, () -> bookingService.reserveSeat(show, seat));
  }
}
