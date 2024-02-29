package com.elhady.usecase.home

import com.elhady.entities.MovieEntity
import com.elhady.usecase.repository.MovieRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val trendingMovieMapper: TrendingMovieMapper
) {

    suspend operator fun invoke(): List<MovieEntity> {
        return movieRepository.getTrendingMovie().map(trendingMovieMapper::map)
    }
}