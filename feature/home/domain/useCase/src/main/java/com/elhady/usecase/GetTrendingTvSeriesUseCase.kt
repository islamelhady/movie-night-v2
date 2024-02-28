package com.elhady.usecase

import com.elhady.entities.MediaEntity
import com.elhady.usecase.repository.SeriesRepository
import javax.inject.Inject

class GetTrendingTvSeriesUseCase @Inject constructor(
    private val repository: SeriesRepository,
    private val trendingMediaMapper: TrendingMediaMapper
) {
    suspend operator fun invoke(): List<MediaEntity> {
        val response = repository.getTrendingTvSries()
        return response?.let(trendingMediaMapper::map) ?: throw Throwable("Not success")
    }
}