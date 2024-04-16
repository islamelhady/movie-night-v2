package com.elhady.movies.core.domain.usecase.usecase.showmore

import androidx.paging.PagingData
import com.elhady.movies.core.domain.entities.MovieEntity
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoreTopRatedByTypeUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): Flow<PagingData<MovieEntity>> {
        return movieRepository.getTopRateMoviesPaging().flow
    }
}