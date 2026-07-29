package com.elhady.movies.feature.home.presentation.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.MovieEntity
import com.elhady.movies.feature.home.presentation.NowPlayingUiState
import javax.inject.Inject

class NowPlayingUiMapper @Inject constructor() : Mapper<MovieEntity, NowPlayingUiState> {
    override fun map(input: MovieEntity): NowPlayingUiState {
        return NowPlayingUiState(
            id = input.id,
            imageUrl = input.imageUrl,
            title = input.title,
        )
    }
}
