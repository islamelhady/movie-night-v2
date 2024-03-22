package com.elhady.movies.presentation.viewmodel.home.mappers

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.TVShowsEntity
import com.elhady.movies.presentation.viewmodel.home.AiringTodayTvShowUiState
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