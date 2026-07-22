package com.elhady.movies.presentation.viewmodel.home.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.common.domain.entities.TVShowsEntity
import com.elhady.movies.presentation.viewmodel.home.TvShowUiState
import javax.inject.Inject

class TvShowUiMapper @Inject constructor()  : Mapper<TVShowsEntity, TvShowUiState> {
    override fun map(input: TVShowsEntity): TvShowUiState {
        return TvShowUiState(
            input.id,
            input.imageUrl,
            input.rate,
        )
    }
}
