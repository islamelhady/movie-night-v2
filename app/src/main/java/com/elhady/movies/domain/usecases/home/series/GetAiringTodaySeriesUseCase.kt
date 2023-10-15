package com.elhady.movies.domain.usecases.home.series

import com.elhady.movies.data.repository.SeriesRepository
import com.elhady.movies.domain.mappers.series.AiringTodaySeriesMapper
import com.elhady.movies.domain.models.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAiringTodaySeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val airingTodaySeriesMapper: AiringTodaySeriesMapper
) {
    suspend operator fun invoke(): Flow<List<Media>> {
        return seriesRepository.getAiringTodaySeries().map { items ->
            items.map {
                airingTodaySeriesMapper.map(it)
            }
        }
    }
}