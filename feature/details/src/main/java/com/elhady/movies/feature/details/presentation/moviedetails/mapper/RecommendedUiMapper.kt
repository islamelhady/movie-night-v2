package com.elhady.movies.feature.details.presentation.moviedetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.RecommendedMovie
import com.elhady.movies.core.ui.state.MediaVerticalUiState
import javax.inject.Inject

class RecommendedUiMapper @Inject constructor() :
    Mapper<RecommendedMovie, MediaVerticalUiState> {
    override fun map(input: RecommendedMovie): MediaVerticalUiState {
        return MediaVerticalUiState(
            id = input.id,
            rate = input.voteAverage,
            imageUrl = input.posterPath,
        )
    }
}
