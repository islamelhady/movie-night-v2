package com.elhady.movies.core.domain.usecase.home

import com.elhady.movies.core.domain.entities.MovieEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val refreshIfNeededUseCase: RefreshIfNeededUseCase
) {
    suspend operator fun invoke(limit: Int = 10): List<MovieEntity> {
        refreshIfNeededUseCase()
        return movieRepository.getUpcomingMoviesFromDatabase()
            .also { if (it.isEmpty()) movieRepository.refreshUpcomingMovies() }
            .take(limit)
    }
}