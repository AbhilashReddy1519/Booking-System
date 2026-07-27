package com.app.bs.booking_system.modules.bookings;

import java.util.UUID;

import com.app.bs.booking_system.modules.seats.Seat;
import com.app.bs.booking_system.modules.shows.Show;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "bookings")
public class Booking {
  @Id
  @GeneratedValue
  private UUID id;

  @Enumerated(EnumType.STRING)
  private BookingStatus status;

  @ManyToOne
  @JoinColumn(name = "show_id")
  @JsonBackReference
  private Show show;

  @ManyToOne
  @JoinColumn(name = "seat_id")
  @JsonBackReference
  private Seat seat;
}
