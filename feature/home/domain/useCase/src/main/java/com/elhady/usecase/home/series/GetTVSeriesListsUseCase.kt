package com.elhady.usecase.home.series

import com.elhady.entities.MediaEntity
import com.elhady.usecase.repository.SeriesRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTVSeriesListsUseCase @Inject constructor(
    private val repository: SeriesRepository,
    private val tvSeriesListsMapper: TVSeriesListsMapper
) {
    suspend operator fun invoke(): List<MediaEntity> {
        return repository.getTVSeriesLists().map(tvSeriesListsMapper::map)
    }
}