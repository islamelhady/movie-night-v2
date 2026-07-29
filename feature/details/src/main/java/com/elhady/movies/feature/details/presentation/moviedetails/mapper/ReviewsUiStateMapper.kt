package com.elhady.movies.feature.details.presentation.moviedetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.common.ReviewEntity
import com.elhady.movies.feature.details.presentation.moviedetails.ReviewUiState
import javax.inject.Inject

class ReviewsUiStateMapper@Inject constructor() :
    Mapper<ReviewEntity, ReviewUiState> {
    override fun map(input: ReviewEntity): ReviewUiState {
        return ReviewUiState(
            name = input.name,
            avatarPath = input.avatarPath,
            content = input.content,
            createdAt = input.createdAt
        )
    }
}
