package com.elhady.movies.core.domain.usecase.tvshow

import androidx.paging.PagingData
import com.elhady.movies.core.domain.model.TVShowsEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularTVShowsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): Flow<PagingData<TVShowsEntity>> {
        return movieRepository.getPopularTVShowsPager().flow
    }
}
