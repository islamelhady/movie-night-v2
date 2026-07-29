package com.elhady.movies.core.domain.usecase.movie

import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.domain.repository.MovieRepository
import com.elhady.movies.core.domain.usecase.common.RefreshIfNeededUseCase
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val refreshIfNeededUseCase: RefreshIfNeededUseCase
) {
    suspend operator fun invoke(limit: Int = 10): List<Movie> {
        refreshIfNeededUseCase()
        return movieRepository.getTrendingMovies()
            .also { if (it.isEmpty()) movieRepository.refreshTrendingMovies() }
            .take(limit).sortedByDescending { it.rate }
    }
}
