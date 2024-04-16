package com.elhady.movies.presentation.viewmodel.moviedetails.mapper

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.moviedetails.MovieDetailsEntity
import com.elhady.movies.presentation.viewmodel.moviedetails.ReviewDetailsUiState
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