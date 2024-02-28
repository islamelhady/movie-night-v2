package com.elhady.usecase.seriesDetails

import com.elhady.entities.SeriesDetailsEntity
import com.elhady.usecase.repository.SeriesRepository
import javax.inject.Inject

class InsertWatchSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val watchHistoryMapper: WatchSeriesHistoryMapper
) {
    suspend operator fun invoke(series: SeriesDetailsEntity) {
        val seriesWatch = watchHistoryMapper.map(series)
        seriesRepository.insertSeriesWatch(seriesWatch)
    }
}