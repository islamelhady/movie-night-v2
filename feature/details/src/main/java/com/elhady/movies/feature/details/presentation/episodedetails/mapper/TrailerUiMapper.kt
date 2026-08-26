package com.elhady.movies.feature.details.presentation.episodedetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.common.YoutubeVideoDetails
import com.elhady.movies.feature.details.presentation.episodedetails.TrailerUiState
import javax.inject.Inject

class TrailerUiMapper @Inject constructor() : Mapper<YoutubeVideoDetails, TrailerUiState> {
    override fun map(input: YoutubeVideoDetails): TrailerUiState {
        return TrailerUiState(
            videoKey = input.key
        )
    }
}
