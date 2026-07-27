package com.app.bs.booking_system.modules.booking_seat;

import java.util.UUID;

import com.app.bs.booking_system.modules.bookings.Booking;
import com.app.bs.booking_system.modules.seats.Seat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "booking_seats")
public class BookingSeat {
  @Id
  @GeneratedValue
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "booking_id")
  private Booking booking;

  @ManyToOne
  @JoinColumn(name = "seat_id")
  private Seat seat;
}
