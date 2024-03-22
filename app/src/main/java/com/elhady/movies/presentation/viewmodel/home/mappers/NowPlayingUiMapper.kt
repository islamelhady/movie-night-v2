package com.elhady.movies.presentation.viewmodel.home.mappers

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.MovieEntity
import com.elhady.movies.presentation.viewmodel.home.NowPlayingUiState
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