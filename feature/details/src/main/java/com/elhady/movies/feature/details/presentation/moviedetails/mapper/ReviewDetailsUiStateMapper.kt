package com.elhady.movies.feature.details.presentation.moviedetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.MovieDetails
import com.elhady.movies.feature.details.presentation.moviedetails.ReviewDetailsUiState
import javax.inject.Inject

class ReviewDetailsUiStateMapper@Inject constructor() :
    Mapper<MovieDetails, ReviewDetailsUiState> {
    override fun map(input: MovieDetails): ReviewDetailsUiState {
        return ReviewDetailsUiState(
            page = input.reviewEntity.page,
            totalPages = input.reviewEntity.totalPages,
            totalReviews = input.reviewEntity.totalResults
        )
    }
}
