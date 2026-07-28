package com.elhady.movies.core.domain.usecase.tvshow

import androidx.paging.PagingData
import com.elhady.movies.core.domain.model.TVShowsEntity
import com.elhady.movies.core.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOnTheAirTVShowsUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(): Flow<PagingData<TVShowsEntity>> {
        return tvShowRepository.getOnTheAirTVShowsPager().flow
    }
}
