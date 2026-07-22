package com.elhady.movies.feature.details.presentation.moviedetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.common.domain.entities.moviedetails.RecommendedMovieEntity
import com.elhady.movies.core.common.presentation.model.MediaVerticalUIState
import javax.inject.Inject

class RecommendedUiStateMapper @Inject constructor() :
    Mapper<RecommendedMovieEntity, MediaVerticalUIState> {
    override fun map(input: RecommendedMovieEntity): MediaVerticalUIState {
        return MediaVerticalUIState(
            id = input.id,
            rate = input.voteAverage,
            imageUrl = input.posterPath,
        )
    }
}
