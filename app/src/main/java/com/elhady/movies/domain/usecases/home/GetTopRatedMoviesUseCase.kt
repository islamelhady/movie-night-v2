package com.elhady.movies.domain.usecases.home

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.mappers.TopRatedMovieMapper
import com.elhady.movies.domain.models.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val topRatedMovieMapper: TopRatedMovieMapper
) {
    suspend operator fun invoke(): List<Media> {
        return movieRepository.getTopRatedMovies().map(topRatedMovieMapper::map)
    }
}