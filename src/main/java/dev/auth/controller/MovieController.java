package dev.auth.controller;


import dev.auth.dto.MovieResponseDTO;
import dev.auth.dto.MovieUpdateRequestDTO;
import dev.auth.model.Movie;
import dev.auth.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/movies")
@RequiredArgsConstructor
public class MovieController {

  private final MovieService movieService;
  
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
  
	@GetMapping("/")
	public ResponseEntity<List<MovieResponseDTO>> getAllMovies() {
		try {
			List<MovieResponseDTO> movies = movieService.getAllMovies();
			return ResponseEntity.ok(movies);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}

	@PostMapping("/update")
	public ResponseEntity<?> updateMovie(@RequestParam Long id, @RequestBody MovieUpdateRequestDTO dto) {
		try {
			MovieResponseDTO updatedMovie = movieService.updateMovie(id, dto);
			return ResponseEntity.ok(updatedMovie);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body("잘못된 요청: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(500).body("서버 오류: " + e.getMessage());
		}
	}
}
