package com.elhady.movies.domain.usecases.seeAllMedia

import androidx.paging.PagingData
import androidx.paging.map
import com.elhady.movies.data.repository.SeriesRepository
import com.elhady.movies.domain.mappers.series.TVShowDtoMapper
import com.elhady.movies.domain.models.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSeeAllSeriesLatestUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val tvShowDtoMapper: TVShowDtoMapper
) {
    operator fun invoke(): Flow<PagingData<Media>> {
        val data = seriesRepository.getAllLatestTV()
        return data.flow.map { pagingData ->
            pagingData.map {
                tvShowDtoMapper.map(it)
            }
        }
    }
}