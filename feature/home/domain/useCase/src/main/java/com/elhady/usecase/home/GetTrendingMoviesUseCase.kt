package com.elhady.usecase.home

import com.elhady.entities.MediaEntity
import com.elhady.usecase.repository.MovieRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val trendingMovieMapper: TrendingMovieMapper
) {

    suspend operator fun invoke(): List<MediaEntity> {
        return movieRepository.getTrendingMovie().map(trendingMovieMapper::map)
    }
}