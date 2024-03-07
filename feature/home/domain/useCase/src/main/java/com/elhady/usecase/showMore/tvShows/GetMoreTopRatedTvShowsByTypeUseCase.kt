package com.elhady.usecase.showMore.tvShows

import androidx.paging.PagingData
import com.elhady.entities.TvShowEntity
import com.elhady.usecase.repository.TvShowsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoreTopRatedTvShowsByTypeUseCase @Inject constructor(
    private val tvShowsRepository: TvShowsRepository
) {
    suspend operator fun invoke(): Flow<PagingData<TvShowEntity>> {
        return tvShowsRepository.getTopRatedTvShowsPaging().flow
    }
}