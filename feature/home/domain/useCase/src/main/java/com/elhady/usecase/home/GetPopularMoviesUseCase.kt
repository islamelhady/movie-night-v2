package com.elhady.usecase.home

import com.elhady.entities.PopularMovieEntity
import com.elhady.usecase.repository.MovieRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieMapper: PopularMovieMapper
) {
    suspend operator fun invoke(): List<PopularMovieEntity> {
        return movieRepository.getPopularMovies().map(movieMapper::map)
    }
}