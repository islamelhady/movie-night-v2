package com.elhady.movies.feature.details.presentation.tvdetails.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.ReviewEntity
import com.elhady.movies.feature.details.presentation.episodedetails.CommentUIState
import javax.inject.Inject

class TvDetailsReviewUiMapper @Inject constructor() :
    Mapper<ReviewEntity, CommentUIState> {
    override fun map(input: List<ReviewEntity>): List<CommentUIState> {
        return input.map(::map)
    }

    override fun map(input: ReviewEntity): CommentUIState {
        return CommentUIState(
            name = input.name,
            content = input.content
        )
    }
}
