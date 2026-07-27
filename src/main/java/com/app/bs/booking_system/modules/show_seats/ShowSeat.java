package com.app.bs.booking_system.modules.show_seats;

import java.util.UUID;

import com.app.bs.booking_system.modules.seats.Seat;
import com.app.bs.booking_system.modules.shows.Show;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
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
@Table(name = "show_seats", uniqueConstraints = {
    @UniqueConstraint(name = "uk_show_seat", columnNames = {"show_id", "seat_id"})
})
public class ShowSeat {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "show_id", nullable = false)
  private Show show;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "seat_id", nullable = false)
  private Seat seat;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private ShowSeatStatus status = ShowSeatStatus.AVAILABLE;

  @Version
  private Long version;
}
