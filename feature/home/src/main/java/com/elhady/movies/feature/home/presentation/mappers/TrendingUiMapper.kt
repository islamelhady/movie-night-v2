package com.elhady.movies.feature.home.presentation.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.feature.home.presentation.TrendingMoviesUiState
import javax.inject.Inject

class TrendingUiMapper @Inject constructor()  : Mapper<Movie, TrendingMoviesUiState> {
    override fun map(input: Movie): TrendingMoviesUiState {
        return TrendingMoviesUiState(
            input.id,
            input.imageUrl,
            input.rate
        )
    }
}
