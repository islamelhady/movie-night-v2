package com.elhady.movies.core.domain.usecase.usecase


import com.elhady.movies.core.domain.entities.GenreEntity
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import javax.inject.Inject

class GetAllGenresMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): List<GenreEntity> {
        movieRepository.refreshGenres()
        return movieRepository.getGenresMovies().sortedBy { it.genreName }
    }
}