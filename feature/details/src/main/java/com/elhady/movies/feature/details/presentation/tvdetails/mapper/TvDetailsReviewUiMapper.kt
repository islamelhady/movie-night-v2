package com.elhady.movies.feature.details.presentation.tvdetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.common.Review
import com.elhady.movies.feature.details.presentation.episodedetails.CommentUiState
import javax.inject.Inject

class TvDetailsReviewUiMapper @Inject constructor() :
    Mapper<Review, CommentUiState> {

    override fun map(input: Review): CommentUiState {
        return CommentUiState(
            name = input.name,
            content = input.content
        )
    }
}
