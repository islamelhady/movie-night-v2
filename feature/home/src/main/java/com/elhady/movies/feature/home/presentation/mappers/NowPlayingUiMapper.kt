package com.elhady.movies.feature.home.presentation.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.feature.home.presentation.NowPlayingUiState
import javax.inject.Inject

class NowPlayingUiMapper @Inject constructor() : Mapper<Movie, NowPlayingUiState> {
    override fun map(input: Movie): NowPlayingUiState {
        return NowPlayingUiState(
            id = input.id,
            imageUrl = input.imageUrl,
            title = input.title,
        )
    }
}
