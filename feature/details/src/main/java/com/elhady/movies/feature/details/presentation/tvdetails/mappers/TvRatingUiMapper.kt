package com.elhady.movies.feature.details.presentation.tvdetails.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.StatusEntity
import com.elhady.movies.feature.details.presentation.tvdetails.TvDetailsUiState
import javax.inject.Inject

class TvRatingUiMapper @Inject constructor() : Mapper<StatusEntity, TvDetailsUiState> {
    override fun map(input: StatusEntity): TvDetailsUiState {
        return TvDetailsUiState(
            ratingSuccess = input.statusMessage
        )
    }

}
