package com.elhady.movies.feature.details.presentation.episodedetails

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.common.YoutubeVideoDetailsEntity
import javax.inject.Inject

class TrailerUiMapper @Inject constructor() : Mapper<YoutubeVideoDetailsEntity, TrailerUiState> {
    override fun map(input: YoutubeVideoDetailsEntity): TrailerUiState {
        return TrailerUiState(
            videoKey = input.key
        )
    }
}
