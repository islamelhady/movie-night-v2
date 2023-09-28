package com.elhady.movies.domain.usecases.home

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.mappers.PopularMovieMapper
import com.elhady.movies.domain.models.PopularMovie
import com.elhady.movies.ui.home.PopularUiState
import com.elhady.movies.utilities.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(): Flow<List<PopularMovie>> {
        return movieRepository.getRefreshPopular()
        }
}
