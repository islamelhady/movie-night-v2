package com.elhady.viewmodel.home.mappers

import com.elhady.entities.TvShowEntity
import com.elhady.mapper.Mapper
import com.elhady.viewmodel.home.homeUiState.OnTheAirTVShowsUiState
import javax.inject.Inject

class OnTheAirTVShowsUiMapper @Inject constructor() : Mapper<TvShowEntity, OnTheAirTVShowsUiState> {
    override fun map(input: TvShowEntity): OnTheAirTVShowsUiState {
        return OnTheAirTVShowsUiState(
            id = input.id,
            rate = input.rate,
            imageUrl = input.imageUrl,
            title = input.name
        )
    }
}