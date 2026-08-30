package com.elhady.movies.feature.details.presentation.moviedetails.mapper

import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.domain.model.common.Review
import com.elhady.movies.feature.details.presentation.moviedetails.ReviewUiState
import javax.inject.Inject

class ReviewsUiMapper @Inject constructor() :
    Mapper<Review, ReviewUiState> {
    override fun map(input: Review): ReviewUiState {
        return ReviewUiState(
            name = input.name,
            avatarPath = input.avatarPath,
            content = input.content,
            createdAt = input.createdAt
        )
    }
}
