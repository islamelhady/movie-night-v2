package com.elhady.movies.core.domain.usecase.usecase.home

import com.elhady.movies.core.domain.entities.MovieEntity
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val refreshIfNeededUseCase: RefreshIfNeededUseCase
) {
    suspend operator fun invoke(limit: Int = 10): List<MovieEntity> {
        refreshIfNeededUseCase()
        return movieRepository.getTrendingMovies()
            .also { if (it.isEmpty()) movieRepository.refreshTrendingMovies() }
            .take(limit).sortedByDescending { it.rate }
    }
}