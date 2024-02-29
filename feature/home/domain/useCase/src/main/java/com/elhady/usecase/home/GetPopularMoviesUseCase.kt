package com.elhady.usecase.home

import com.elhady.entities.PopularMovieEntity
import com.elhady.usecase.repository.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(): List<PopularMovieEntity> {
        return movieRepository.getPopularMovies()
    }
}