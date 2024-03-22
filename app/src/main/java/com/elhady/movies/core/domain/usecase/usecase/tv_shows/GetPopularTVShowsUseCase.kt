package com.elhady.movies.core.domain.usecase.usecase.tv_shows

import androidx.paging.PagingData
import com.elhady.movies.core.domain.entities.TVShowsEntity
import com.elhady.movies.core.domain.usecase.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularTVShowsUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(): Flow<PagingData<TVShowsEntity>> {
        return tvShowRepository.getPopularTVShowsPager().flow
    }
}