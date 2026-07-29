package com.elhady.movies.feature.details.presentation.tvdetails.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.common.Status
import com.elhady.movies.feature.details.presentation.tvdetails.TvDetailsUiState
import javax.inject.Inject

class TvRatingUiMapper @Inject constructor() : Mapper<Status, TvDetailsUiState> {
    override fun map(input: Status): TvDetailsUiState {
        return TvDetailsUiState(
            ratingSuccess = input.statusMessage
        )
    }

}
