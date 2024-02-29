package com.elhady.usecase.home

import com.elhady.entities.MovieEntity
import com.elhady.usecase.repository.MovieRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val upcomingMovieMapper: UpcomingMovieMapper
) {
    suspend operator fun invoke(): List<MovieEntity> {
        return movieRepository.getUpcomingMovies().map(upcomingMovieMapper::map)
    }
}