package com.elhady.movies.domain.usecases

import com.elhady.movies.data.repository.SeriesRepository
import com.elhady.movies.domain.mappers.TrendingMediaMapper
import com.elhady.movies.domain.models.Media
import javax.inject.Inject

class GetTrendingTvSeriesUseCase @Inject constructor(
    private val repository: SeriesRepository,
    private val trendingMediaMapper: TrendingMediaMapper
) {
    suspend operator fun invoke(): List<Media> {
        val response = repository.getTrendingTvSries()
        return response?.let { list ->
            list.map {
                trendingMediaMapper.map(it)
            }
        } ?: throw Throwable("Not success")
    }
}