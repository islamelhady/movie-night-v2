package com.elhady.movies.presentation.viewmodel.tvdetails.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.common.domain.entities.YoutubeVideoDetailsEntity
import com.elhady.movies.presentation.viewmodel.tvdetails.TvDetailsUiState

class TvShowYoutubeVideoDetailsUiMapper : Mapper<YoutubeVideoDetailsEntity, TvDetailsUiState> {
    override fun map(input: YoutubeVideoDetailsEntity): TvDetailsUiState {
        return TvDetailsUiState(youtubeKeyId = input.key)
    }
}
