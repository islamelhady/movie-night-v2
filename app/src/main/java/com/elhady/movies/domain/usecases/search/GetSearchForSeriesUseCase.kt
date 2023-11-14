package com.elhady.movies.domain.usecases.search

import androidx.paging.PagingData
import androidx.paging.map
import com.elhady.movies.data.repository.SeriesRepository
import com.elhady.movies.domain.mappers.series.SeriesDtoMapper
import com.elhady.movies.domain.models.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSearchForSeriesUseCase @Inject constructor(
    private val repository: SeriesRepository,
    private val seriesDtoMapper: SeriesDtoMapper
) {
    suspend operator fun invoke(seriesQuery: String): Flow<PagingData<Media>> {
        return repository.searchForSeriesPager(query = seriesQuery).flow.map { pagingData ->
            pagingData.map {
                seriesDtoMapper.map(it)
            }
        }
    }
}