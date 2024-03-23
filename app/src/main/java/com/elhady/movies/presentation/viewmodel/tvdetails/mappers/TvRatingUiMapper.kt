package com.elhady.movies.presentation.viewmodel.tvdetails.mappers

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.StatusEntity
import com.elhady.movies.presentation.viewmodel.tvdetails.TvDetailsUiState
import javax.inject.Inject

class TvRatingUiMapper @Inject constructor() : Mapper<StatusEntity, TvDetailsUiState> {
    override fun map(input: StatusEntity): TvDetailsUiState {
        return TvDetailsUiState(
            ratingSuccess = input.statusMessage
        )
    }

}