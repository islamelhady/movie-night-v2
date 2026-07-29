package com.elhady.movies.core.domain.usecase.tvshow

import androidx.paging.PagingData
import com.elhady.movies.core.domain.model.tvshow.TvShows
import com.elhady.movies.core.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAiringTodayTVShowsUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(): Flow<PagingData<TvShows>> {
        return tvShowRepository.getAiringTodayTVShowsPager().flow
    }
}
