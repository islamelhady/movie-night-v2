package com.elhady.movies.feature.home.presentation.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.MovieEntity
import com.elhady.movies.feature.home.presentation.PopularMoviesUiState
import javax.inject.Inject

class PopularMoviesUiMapper @Inject constructor() :
    Mapper<MovieEntity, PopularMoviesUiState> {
    override fun map(input: MovieEntity): PopularMoviesUiState {
        return PopularMoviesUiState(
            input.id,
            input.imageUrl,
            input.rate
        )
    }
}
