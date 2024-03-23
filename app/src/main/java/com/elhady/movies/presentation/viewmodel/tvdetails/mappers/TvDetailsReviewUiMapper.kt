package com.elhady.movies.presentation.viewmodel.tvdetails.mappers

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.ReviewEntity
import com.elhady.movies.presentation.viewmodel.common.model.CommentUIState
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