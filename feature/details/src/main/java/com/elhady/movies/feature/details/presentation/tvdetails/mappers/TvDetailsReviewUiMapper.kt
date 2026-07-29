package com.elhady.movies.feature.details.presentation.tvdetails.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.common.Review
import com.elhady.movies.feature.details.presentation.episodedetails.CommentUIState
import javax.inject.Inject

class TvDetailsReviewUiMapper @Inject constructor() :
    Mapper<Review, CommentUIState> {
    override fun map(input: List<Review>): List<CommentUIState> {
        return input.map(::map)
    }

    override fun map(input: Review): CommentUIState {
        return CommentUIState(
            name = input.name,
            content = input.content
        )
    }
}
