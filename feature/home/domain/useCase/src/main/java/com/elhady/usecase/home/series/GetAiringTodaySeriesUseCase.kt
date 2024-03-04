package com.elhady.usecase.home.series

import com.elhady.entities.TvShowEntity
import com.elhady.usecase.repository.SeriesRepository
import javax.inject.Inject

class GetAiringTodaySeriesUseCase @Inject constructor(
    private val repository: SeriesRepository,
) {
    suspend operator fun invoke(limit: Int = 10): List<TvShowEntity> {
        return repository.getAiringTodayTVShowsFromDatabase()
            .also {
                if (it.isEmpty()) repository.refreshAiringTodayTVShows()
            }.take(limit)
    }
}