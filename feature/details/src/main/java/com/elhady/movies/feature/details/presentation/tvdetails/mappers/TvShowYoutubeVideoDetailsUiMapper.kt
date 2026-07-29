package com.elhady.movies.feature.details.presentation.tvdetails.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.common.YoutubeVideoDetailsEntity
import com.elhady.movies.feature.details.presentation.tvdetails.TvDetailsUiState

class TvShowYoutubeVideoDetailsUiMapper : Mapper<YoutubeVideoDetailsEntity, TvDetailsUiState> {
    override fun map(input: YoutubeVideoDetailsEntity): TvDetailsUiState {
        return TvDetailsUiState(youtubeKeyId = input.key)
    }
}
