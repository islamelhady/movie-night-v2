package com.elhady.movies.domain.usecases.home

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.mappers.PopularMovieMapper
import com.elhady.movies.domain.models.PopularMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieMapper: PopularMovieMapper
) {
    suspend operator fun invoke(): List<PopularMovie> {
        return movieRepository.getPopularMovies().map(movieMapper::map)
    }
}