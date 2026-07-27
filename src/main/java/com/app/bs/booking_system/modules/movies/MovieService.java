package com.app.bs.booking_system.modules.movies;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MovieService {
  private final MovieRepository movieRepository;

  public MovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public Movie createMovie(Movie movie) {
    return movieRepository.save(movie);
  }

  public List<Movie> getMovies() {
    return movieRepository.findAll();
  }
}
