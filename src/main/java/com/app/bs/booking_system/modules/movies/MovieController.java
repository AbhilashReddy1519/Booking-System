package com.app.bs.booking_system.modules.movies;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/movie")
public class MovieController {
  private final MovieService movieService;

  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @PostMapping("")
  public Movie createMovie(@RequestBody Movie movie) {
    return movieService.createMovie(movie);
  }

  @GetMapping("")
  public List<Movie> getMovies() {
    return movieService.getMovies();
  }
  
}
