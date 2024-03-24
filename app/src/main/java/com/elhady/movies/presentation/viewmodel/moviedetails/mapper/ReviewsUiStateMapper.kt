package com.elhady.movies.presentation.viewmodel.moviedetails.mapper

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.ReviewEntity
import com.elhady.movies.presentation.viewmodel.moviedetails.ReviewUiState
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