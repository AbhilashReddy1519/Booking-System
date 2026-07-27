package com.app.bs.booking_system.modules.screens;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

import com.app.bs.booking_system.modules.seats.Seat;
import com.app.bs.booking_system.modules.shows.Show;
import com.app.bs.booking_system.modules.theater.Theater;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="screens")
public class Screen {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank(message = "Screen name should not be empty")
  private String name;

  @ManyToOne
  @JoinColumn(name = "theater_id")
  @JsonBackReference("theater-screen")
  private Theater theater;

  @OneToMany(mappedBy = "screen")
  @JsonManagedReference("screen-show")
  private List<Show> shows;

  @OneToMany(mappedBy = "screen")
  private List<Seat> seats;
}
