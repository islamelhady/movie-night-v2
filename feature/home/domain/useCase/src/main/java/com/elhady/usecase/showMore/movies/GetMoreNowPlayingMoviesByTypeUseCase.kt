package com.elhady.usecase.showMore.movies

import androidx.paging.PagingData
import com.elhady.entities.MovieEntity
import com.elhady.usecase.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoreNowPlayingMoviesByTypeUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): Flow<PagingData<MovieEntity>> {
        return movieRepository.getNowPlayingMoviesPaging().flow
    }
}