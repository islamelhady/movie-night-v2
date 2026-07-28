package com.elhady.movies.core.domain.usecase.movie

import com.elhady.movies.core.domain.model.movie.MovieEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import com.elhady.movies.core.domain.usecase.common.RefreshIfNeededUseCase
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val refreshIfNeededUseCase: RefreshIfNeededUseCase
) {
    suspend operator fun invoke(limit: Int = 10): List<MovieEntity> {
        refreshIfNeededUseCase()
        return movieRepository.getPopularMoviesFromDatabase()
            .also { if (it.isEmpty()) movieRepository.refreshPopularMovies() }
            .take(limit)
    }
}
