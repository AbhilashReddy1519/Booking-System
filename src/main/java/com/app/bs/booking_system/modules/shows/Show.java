package com.app.bs.booking_system.modules.shows;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.app.bs.booking_system.modules.bookings.Booking;
import com.app.bs.booking_system.modules.languages.Language;
import com.app.bs.booking_system.modules.movies.Movie;
import com.app.bs.booking_system.modules.screens.Screen;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shows")
public class Show {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NonNull
  private LocalDateTime startDateTime;

  @NonNull
  private LocalDateTime endDateTime;

  @ManyToOne
  @JoinColumn(name = "movie_id")
  @JsonBackReference
  private Movie movie;

  @ManyToOne 
  @JoinColumn(name = "screen_id")
  @JsonBackReference
  private Screen screen;

  @ManyToOne
  @JoinColumn(name = "language_id")
  @JsonBackReference
  private Language language;

  @OneToMany(mappedBy = "show" , cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<Booking> bookings;
}
