package com.elhady.usecase.home

import com.elhady.entities.MediaEntity
import com.elhady.usecase.repository.MovieRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val topRatedMovieMapper: TopRatedMovieMapper
) {
    suspend operator fun invoke(): List<MediaEntity> {
        return movieRepository.getTopRatedMovies().map(topRatedMovieMapper::map)
    }
}