package com.elhady.movies.presentation.viewmodel.episodedetails

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.YoutubeVideoDetailsEntity
import javax.inject.Inject

class TrailerUiMapper @Inject constructor() : Mapper<YoutubeVideoDetailsEntity, TrailerUiState> {
    override fun map(input: YoutubeVideoDetailsEntity): TrailerUiState {
        return TrailerUiState(
            videoKey = input.key
        )
    }
}