package com.elhady.movies.feature.details.presentation.tvdetails.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.tvshow.TvShowEntity
import com.elhady.movies.core.ui.model.MediaVerticalUIState
import com.elhady.movies.feature.details.presentation.tvdetails.TvDetailsUiState
import javax.inject.Inject

class TvShowUiMapper @Inject constructor() : Mapper<List<TvShowEntity>, TvDetailsUiState> {
    override fun map(input: List<TvShowEntity>): TvDetailsUiState {
        return TvDetailsUiState(
            recommended = input.map { tvShow ->
                MediaVerticalUIState(
                    id = tvShow.id,
                    imageUrl = tvShow.imageUrl,
                    rate = tvShow.rate
                )
            }
        )
    }
}
