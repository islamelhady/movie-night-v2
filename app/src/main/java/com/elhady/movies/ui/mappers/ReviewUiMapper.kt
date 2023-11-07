package com.elhady.movies.ui.mappers

import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.Review
import com.elhady.movies.ui.models.ReviewUiState
import javax.inject.Inject

class ReviewUiMapper @Inject constructor() : Mapper<Review, ReviewUiState> {
    override fun map(input: Review): ReviewUiState {
        return ReviewUiState(
            userImage = input.userImage,
            userName = input.userName,
            name = input.name,
            content = input.content,
            rating = input.rating,
            createDate = input.createDate
        )
    }
}