package com.elhady.movies.feature.details.presentation.tvdetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.tvshow.TvShow
import com.elhady.movies.core.ui.state.MediaVerticalUiState
import com.elhady.movies.feature.details.presentation.tvdetails.TvDetailsUiState
import javax.inject.Inject

class TvShowUiMapper @Inject constructor() : Mapper<List<TvShow>, TvDetailsUiState> {
    override fun map(input: List<TvShow>): TvDetailsUiState {
        return TvDetailsUiState(
            recommended = input.map { tvShow ->
                MediaVerticalUiState(
                    id = tvShow.id,
                    imageUrl = tvShow.imageUrl,
                    rate = tvShow.rate
                )
            }
        )
    }
}
