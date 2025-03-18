package dev.auth.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.auth.dto.MovieUpdateRequestDTO;
import dev.auth.dto.MovieResponseDTO;
import dev.auth.service.MovieService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/movie")
@RequiredArgsConstructor
public class MovieController {
	private final MovieService movieService;

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
