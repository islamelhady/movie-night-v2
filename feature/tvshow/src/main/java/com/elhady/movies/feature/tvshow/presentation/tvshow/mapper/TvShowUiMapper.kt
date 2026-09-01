package com.elhady.movies.feature.tvshow.presentation.tvshow.mapper

import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.domain.model.tvshow.TvShows
import com.elhady.movies.feature.tvshow.presentation.tvshow.ShowUiState

import javax.inject.Inject

class TvShowUiMapper @Inject constructor() :
    Mapper<TvShows, ShowUiState> {
    override fun map(input: TvShows): ShowUiState {
        return ShowUiState(
            tvId = input.id,
            imageUrl = input.imageUrl,
            rate = input.rate
        )
    }
}
