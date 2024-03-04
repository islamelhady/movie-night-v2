package com.elhady.usecase.home.movies

import com.elhady.entities.MovieEntity
import com.elhady.usecase.repository.MovieRepository
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(limit: Int = 10): List<MovieEntity> {
        return movieRepository.getNowPlayingMoviesFromDatabase()
            .also { if (it.isEmpty()) movieRepository.refreshNowPlayingMovies() }
            .take(limit)
    }
}