package com.app.bs.booking_system.modules.languages;

import java.util.List;
import java.util.UUID;

import com.app.bs.booking_system.modules.movies.Movie;
import com.app.bs.booking_system.modules.shows.Show;
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
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "languages")
public class Language {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank(message = "Language should not be empty")
  private String language;

  @ManyToOne
  @JoinColumn(name = "movie_id")
  @JsonBackReference
  private Movie movie;

  @OneToMany(mappedBy = "language", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<Show> shows;

}
