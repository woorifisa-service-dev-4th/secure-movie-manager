package dev.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.auth.model.Movie;

public interface MovieRepository extends JpaRepository<Movie,Long> {
}
