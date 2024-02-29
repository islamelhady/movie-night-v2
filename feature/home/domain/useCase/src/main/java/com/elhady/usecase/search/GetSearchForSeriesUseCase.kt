package com.elhady.usecase.search

import androidx.paging.PagingData
import androidx.paging.map
import com.elhady.entities.MovieEntity
import com.elhady.usecase.repository.SeriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSearchForSeriesUseCase @Inject constructor(
    private val repository: SeriesRepository,
    private val seriesDtoMapper: SeriesDtoMapper
) {
    suspend operator fun invoke(seriesQuery: String): Flow<PagingData<MovieEntity>> {
        return repository.searchForSeriesPager(query = seriesQuery).flow.map { pagingData ->
            pagingData.map {
                seriesDtoMapper.map(it)
            }
        }
    }
}