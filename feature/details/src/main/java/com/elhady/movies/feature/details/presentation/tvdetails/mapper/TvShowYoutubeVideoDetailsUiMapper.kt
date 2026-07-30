package com.elhady.movies.feature.details.presentation.tvdetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.common.YoutubeVideoDetails
import com.elhady.movies.feature.details.presentation.tvdetails.TvDetailsUiState

class TvShowYoutubeVideoDetailsUiMapper : Mapper<YoutubeVideoDetails, TvDetailsUiState> {
    override fun map(input: YoutubeVideoDetails): TvDetailsUiState {
        return TvDetailsUiState(youtubeKeyId = input.key)
    }
}
