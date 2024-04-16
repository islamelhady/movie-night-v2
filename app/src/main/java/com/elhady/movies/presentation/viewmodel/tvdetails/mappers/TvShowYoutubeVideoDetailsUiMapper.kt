package com.elhady.movies.presentation.viewmodel.tvdetails.mappers

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.YoutubeVideoDetailsEntity
import com.elhady.movies.presentation.viewmodel.tvdetails.TvDetailsUiState

class TvShowYoutubeVideoDetailsUiMapper : Mapper<YoutubeVideoDetailsEntity, TvDetailsUiState> {
    override fun map(input: YoutubeVideoDetailsEntity): TvDetailsUiState {
        return TvDetailsUiState(youtubeKeyId = input.key)
    }
}