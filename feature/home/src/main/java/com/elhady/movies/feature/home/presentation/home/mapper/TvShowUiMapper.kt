package com.elhady.movies.feature.home.presentation.home.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.tvshow.TvShows
import com.elhady.movies.feature.home.presentation.home.TvShowUiState
import javax.inject.Inject

class TvShowUiMapper @Inject constructor()  : Mapper<TvShows, TvShowUiState> {
    override fun map(input: TvShows): TvShowUiState {
        return TvShowUiState(
            input.id,
            input.imageUrl,
            input.rate,
        )
    }
}
