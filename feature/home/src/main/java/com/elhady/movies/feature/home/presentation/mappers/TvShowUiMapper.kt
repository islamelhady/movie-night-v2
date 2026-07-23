package com.elhady.movies.feature.home.presentation.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.TVShowsEntity
import com.elhady.movies.feature.home.presentation.TvShowUiState
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
