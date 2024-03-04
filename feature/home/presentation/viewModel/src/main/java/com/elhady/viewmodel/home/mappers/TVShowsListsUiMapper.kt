package com.elhady.viewmodel.home.mappers

import com.elhady.entities.TvShowEntity
import com.elhady.mapper.Mapper
import com.elhady.viewmodel.home.homeUiState.TVShowsListsUiState
import javax.inject.Inject

class TVShowsListsUiMapper @Inject constructor() : Mapper<TvShowEntity, TVShowsListsUiState> {
    override fun map(input: TvShowEntity): TVShowsListsUiState {
        return TVShowsListsUiState(
            id = input.id,
            rate = input.rate,
            imageUrl = input.imageUrl,
            title = input.name
        )
    }
}