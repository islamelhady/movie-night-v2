package com.elhady.movies.core.domain.usecase.usecase.tvshows

import androidx.paging.PagingData
import com.elhady.movies.core.domain.entities.TVShowsEntity
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedTVShowsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): Flow<PagingData<TVShowsEntity>> {
        return movieRepository.getTopRatedTVShowsPager().flow
    }
}