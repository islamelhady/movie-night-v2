package com.elhady.movies.feature.home.presentation.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.common.domain.entities.MovieEntity
import com.elhady.movies.feature.home.presentation.TrendingMoviesUiState
import javax.inject.Inject

class TrendingUiMapper @Inject constructor()  : Mapper<MovieEntity, TrendingMoviesUiState> {
    override fun map(input: MovieEntity): TrendingMoviesUiState {
        return TrendingMoviesUiState(
            input.id,
            input.imageUrl,
            input.rate
        )
    }
}
