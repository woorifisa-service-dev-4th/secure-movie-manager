package dev.auth.controller;

import dev.auth.model.Movie;
import dev.auth.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/movies")
public class MovieController {

  private final MovieService movieService;

  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @PostMapping
  public String createMovie(@ModelAttribute Movie movie) {
    movieService.createMovie(movie);
    return "movies";
  }

  @PostMapping("/delete/{id}")
  public String deleteMovie(@PathVariable Long id) {
    movieService.deleteMovie(id);
    return "movies";
  }
}
