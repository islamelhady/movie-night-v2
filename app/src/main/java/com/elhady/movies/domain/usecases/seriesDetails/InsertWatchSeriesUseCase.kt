package com.elhady.movies.domain.usecases.seriesDetails

import com.elhady.movies.data.repository.SeriesRepository
import com.elhady.movies.domain.mappers.series.WatchSeriesHistoryMapper
import com.elhady.movies.domain.models.SeriesDetails
import javax.inject.Inject

class InsertWatchSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val watchHistoryMapper: WatchSeriesHistoryMapper
) {
    suspend operator fun invoke(series: SeriesDetails) {
        val seriesWatch = watchHistoryMapper.map(series)
        seriesRepository.insertSeriesWatch(seriesWatch)
    }
}