package com.elhady.viewmodel.mappers

import com.elhady.mapper.Mapper
import com.elhady.viewmodel.models.ReviewUiState
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