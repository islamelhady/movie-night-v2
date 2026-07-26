package com.elhady.movies.feature.home.presentation.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.TVShowsEntity
import com.elhady.movies.feature.home.presentation.AiringTodayTvShowUiState
import javax.inject.Inject

class AiringTodayUiMapper @Inject constructor()  : Mapper<TVShowsEntity, AiringTodayTvShowUiState> {
    override fun map(input: TVShowsEntity): AiringTodayTvShowUiState {
        return AiringTodayTvShowUiState(
            input.id,
            input.imageUrl,
            input.rate
        )
    }
}
