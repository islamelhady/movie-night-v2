package com.elhady.movies.feature.home.presentation.home.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.tvshow.TvShows
import com.elhady.movies.feature.home.presentation.home.AiringTodayTvShowUiState
import javax.inject.Inject

class AiringTodayUiMapper @Inject constructor()  : Mapper<TvShows, AiringTodayTvShowUiState> {
    override fun map(input: TvShows): AiringTodayTvShowUiState {
        return AiringTodayTvShowUiState(
            input.id,
            input.imageUrl,
            input.rate
        )
    }
}
