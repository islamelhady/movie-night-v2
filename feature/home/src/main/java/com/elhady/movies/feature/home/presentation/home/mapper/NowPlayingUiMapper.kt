package com.elhady.movies.feature.home.presentation.home.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.feature.home.presentation.home.NowPlayingMovieUiState
import javax.inject.Inject

class NowPlayingUiMapper @Inject constructor() : Mapper<Movie, NowPlayingMovieUiState> {
    override fun map(input: Movie): NowPlayingMovieUiState {
        return NowPlayingMovieUiState(
            id = input.id,
            imageUrl = input.imageUrl,
            title = input.title,
        )
    }
}
