package com.elhady.movies.core.domain.usecase.movie

import com.elhady.movies.core.domain.model.GenreEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetAllGenresMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): List<GenreEntity> {
        movieRepository.refreshGenres()
        return movieRepository.getGenresMovies().sortedBy { it.genreName }
    }
}