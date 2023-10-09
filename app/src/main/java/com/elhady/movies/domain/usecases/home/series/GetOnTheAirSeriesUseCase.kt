package com.elhady.movies.domain.usecases.home.series

import com.elhady.movies.data.repository.SeriesRepository
import com.elhady.movies.domain.mappers.series.OnTheAirSeriesMapper
import com.elhady.movies.domain.models.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetOnTheAirSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val onTheAirSeriesMapper: OnTheAirSeriesMapper
) {
    suspend operator fun invoke(): Flow<List<Media>>{
        return seriesRepository.getOnTheAirSeries().map { items ->
            items.map(onTheAirSeriesMapper::map)
        }
    }
}