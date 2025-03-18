package dev.auth.dto;

import dev.auth.model.Movie;
import lombok.Getter;

@Getter
public class MovieResponseDTO {
	private Long id;
	private String name;

	public MovieResponseDTO(Movie movie) {
		this.id = movie.getId();
		this.name = movie.getName();
	}
}
