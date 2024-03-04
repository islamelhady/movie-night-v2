package com.elhady.usecase.home.movies

import com.elhady.entities.MovieEntity
import com.elhady.usecase.repository.MovieRepository
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {

    suspend operator fun invoke(limit: Int = 10): List<MovieEntity> {
        return movieRepository.getTrendingMovieFromDatabase()
            .also { if (it.isEmpty()) movieRepository.refreshUpcomingMovies()}
            .take(limit)
    }
}