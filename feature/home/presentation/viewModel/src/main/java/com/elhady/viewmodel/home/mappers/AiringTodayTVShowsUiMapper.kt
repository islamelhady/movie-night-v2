package com.elhady.viewmodel.home.mappers

import com.elhady.entities.TvShowEntity
import com.elhady.mapper.Mapper
import com.elhady.viewmodel.home.homeUiState.AiringTodayTVShowsUiState
import javax.inject.Inject

class AiringTodayTVShowsUiMapper @Inject constructor() : Mapper<TvShowEntity, AiringTodayTVShowsUiState> {
    override fun map(input: TvShowEntity): AiringTodayTVShowsUiState {
        return AiringTodayTVShowsUiState(
            id = input.id,
            rate = input.rate,
            imageUrl = input.imageUrl,
            title = input.name
        )
    }
}