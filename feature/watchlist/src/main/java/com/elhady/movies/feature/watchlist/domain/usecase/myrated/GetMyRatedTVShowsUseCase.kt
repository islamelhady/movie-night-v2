package com.elhady.movies.feature.watchlist.domain.usecase.myrated

import androidx.paging.PagingData
import com.elhady.movies.core.common.domain.entities.myrated.MyRatedTvShowEntity
import com.elhady.movies.core.common.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetMyRatedTVShowsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): Flow<PagingData<MyRatedTvShowEntity>> {
        return movieRepository.getRatedTvShows().flow
    }
}
