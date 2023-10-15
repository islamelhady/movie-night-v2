package com.elhady.movies.domain.usecases.home.series

import com.elhady.movies.data.repository.SeriesRepository
import com.elhady.movies.domain.mappers.series.TVSeriesListsMapper
import com.elhady.movies.domain.models.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTVSeriesListsUseCase @Inject constructor(
    private val repository: SeriesRepository,
    private val tvSeriesListsMapper: TVSeriesListsMapper
) {
    suspend operator fun invoke(): Flow<List<Media>> {
        return repository.getTVSeriesLists().map { lists ->
            lists.map {
                tvSeriesListsMapper.map(it)
            }
        }
    }
}