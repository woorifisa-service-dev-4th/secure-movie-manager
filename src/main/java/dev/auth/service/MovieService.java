package dev.auth.service;

import dev.auth.model.Movie;
import dev.auth.repository.MovieRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MovieService {

  private final MovieRepository movieRepository;

  public MovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  // TODO create 권한도 추가
  @PreAuthorize("hasRole('ADMIN')")
  @Transactional
  public void createMovie(Movie movie) {
    movieRepository.save(movie);
  }
  // TODO 삭제 권한도 추가
  @PreAuthorize("hasRole('ADMIN')")
  @Transactional
  public void deleteMovie(Long id) {
    if (!movieRepository.existsById(id)) {
      throw new IllegalArgumentException("해당 영화가 존재하지 않습니다.");
    }
    movieRepository.deleteById(id);
  }

}
