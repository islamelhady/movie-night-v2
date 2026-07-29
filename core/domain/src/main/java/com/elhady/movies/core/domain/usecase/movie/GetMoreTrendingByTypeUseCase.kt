package com.elhady.movies.core.domain.usecase.movie

import androidx.paging.PagingData
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoreTrendingByTypeUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): Flow<PagingData<Movie>> {
        return movieRepository.getTrendingMoviesPaging().flow
    }
}
