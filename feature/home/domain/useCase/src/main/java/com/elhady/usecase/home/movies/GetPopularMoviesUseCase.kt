package com.elhady.usecase.home.movies

import com.elhady.entities.PopularMovieEntity
import com.elhady.usecase.repository.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(limit: Int = 10): List<PopularMovieEntity> {
        return movieRepository.getPopularMoviesFromDatabase()
            .also {
                if (it.isEmpty()) movieRepository.refreshPopularMovies()
            }.take(limit)
    }
}