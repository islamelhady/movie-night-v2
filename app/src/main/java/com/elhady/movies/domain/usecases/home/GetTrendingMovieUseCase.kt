package com.elhady.movies.domain.usecases.home

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.mappers.TrendingMovieMapper
import com.elhady.movies.domain.models.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTrendingMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val trendingMovieMapper: TrendingMovieMapper
) {

    suspend operator fun invoke(): Flow<List<Media>> {
        return movieRepository.getTrendingMovie().map { items ->
            items.map(trendingMovieMapper::map)
        }
    }

}