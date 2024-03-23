package com.elhady.movies.core.domain.usecase.usecase.myrated

import androidx.paging.PagingData
import com.elhady.movies.core.domain.entities.myrated.MyRatedMovieEntity
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetMyRatedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): Flow<PagingData<MyRatedMovieEntity>> {
        return movieRepository.getRatedMovies().flow
    }
}