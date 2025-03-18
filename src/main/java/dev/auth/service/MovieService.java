package dev.auth.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import dev.auth.dto.MovieResponseDTO;
import dev.auth.dto.MovieUpdateRequestDTO;
import dev.auth.model.Movie;
import dev.auth.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MovieService {
	private final MovieRepository movieRepository;


	// 영화 조회
	@PreAuthorize("hasAuthority('READ')")
	public List<MovieResponseDTO> getAllMovies() {
		return movieRepository.findAll().stream()
			.map(movie -> new MovieResponseDTO(movie))
			.toList();
	}
	// 영화 수정
	@PreAuthorize("hasAuthority('UPDATE')")
	public MovieResponseDTO updateMovie(Long id, MovieUpdateRequestDTO dto) {
		Movie movie = movieRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("영화를 찾을 수 없습니다."));

		movie.setName(dto.getName());
		return new MovieResponseDTO(movieRepository.save(movie));
	}

  @PreAuthorize("hasRole('ADMIN')")
  @Transactional
  public void createMovie(Movie movie) {
    movieRepository.save(movie);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @Transactional
  public void deleteMovie(Long id) {
    if (!movieRepository.existsById(id)) {
      throw new IllegalArgumentException("해당 영화가 존재하지 않습니다.");
    }
    movieRepository.deleteById(id);
  }
}
