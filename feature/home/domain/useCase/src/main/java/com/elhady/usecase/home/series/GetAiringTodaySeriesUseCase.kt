package com.elhady.usecase.home.series

import com.elhady.entities.MovieEntity
import com.elhady.usecase.repository.SeriesRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAiringTodaySeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val airingTodaySeriesMapper: AiringTodaySeriesMapper
) {
    suspend operator fun invoke(): List<MovieEntity> {
        return seriesRepository.getAiringTodaySeries().map {
                airingTodaySeriesMapper.map(it)
        }
    }
}