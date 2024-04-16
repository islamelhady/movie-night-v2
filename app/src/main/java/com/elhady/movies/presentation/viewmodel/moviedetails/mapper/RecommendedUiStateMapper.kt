package com.elhady.movies.presentation.viewmodel.moviedetails.mapper

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.moviedetails.RecommendedMovieEntity
import com.elhady.movies.presentation.viewmodel.common.model.MediaVerticalUIState
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