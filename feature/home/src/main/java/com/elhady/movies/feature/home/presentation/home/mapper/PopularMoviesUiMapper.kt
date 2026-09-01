package com.elhady.movies.feature.home.presentation.home.mapper

import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.feature.home.presentation.home.PopularMovieUiState
import javax.inject.Inject

class PopularMoviesUiMapper @Inject constructor() :
    Mapper<Movie, PopularMovieUiState> {
    override fun map(input: Movie): PopularMovieUiState {
        return PopularMovieUiState(
            id = input.id,
            imageUrl = input.imageUrl,
            rate = input.rate
        )
    }
}
