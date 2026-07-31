package com.elhady.movies.feature.home.presentation.home.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.feature.home.presentation.home.PopularMoviesUiState
import javax.inject.Inject

class PopularMoviesUiMapper @Inject constructor() :
    Mapper<Movie, PopularMoviesUiState> {
    override fun map(input: Movie): PopularMoviesUiState {
        return PopularMoviesUiState(
            input.id,
            input.imageUrl,
            input.rate
        )
    }
}
