package com.elhady.movies.presentation.viewmodel.tvdetails.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.common.domain.entities.ReviewEntity
import com.elhady.movies.core.common.presentation.model.CommentUIState
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
