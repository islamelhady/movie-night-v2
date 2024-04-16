package com.elhady.movies.presentation.viewmodel.tvdetails.mappers

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.TvShowEntity
import com.elhady.movies.presentation.viewmodel.common.model.MediaVerticalUIState
import com.elhady.movies.presentation.viewmodel.tvdetails.TvDetailsUiState
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