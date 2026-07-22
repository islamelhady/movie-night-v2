package com.elhady.movies.feature.details.presentation.moviedetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.common.domain.entities.moviedetails.MovieDetailsEntity
import com.elhady.movies.feature.details.presentation.moviedetails.ReviewDetailsUiState
import javax.inject.Inject

class ReviewDetailsUiStateMapper@Inject constructor() :
    Mapper<MovieDetailsEntity, ReviewDetailsUiState> {
    override fun map(input: MovieDetailsEntity): ReviewDetailsUiState {
        return ReviewDetailsUiState(
            page = input.reviewEntity.page,
            totalPages = input.reviewEntity.totalPages,
            totalReviews = input.reviewEntity.totalResults
        )
    }
}
