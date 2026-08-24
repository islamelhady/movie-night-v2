package com.elhady.movies.feature.home.presentation.home.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.feature.home.presentation.home.TrendingMovieUiState
import javax.inject.Inject

class TrendingUiMapper @Inject constructor()  : Mapper<Movie, TrendingMovieUiState> {
    override fun map(input: Movie): TrendingMovieUiState {
        return TrendingMovieUiState(
            id = input.id,
            imageUrl = input.imageUrl,
            rate = input.rate
        )
    }
}
