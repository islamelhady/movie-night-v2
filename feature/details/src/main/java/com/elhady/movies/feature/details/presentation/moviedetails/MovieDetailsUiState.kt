package com.elhady.movies.feature.details.presentation.moviedetails

import com.elhady.movies.core.common.presentation.model.MediaVerticalUIState
import com.elhady.movies.core.common.presentation.model.PeopleUIState
import com.elhady.movies.core.common.presentation.model.UserListUi


import com.elhady.movies.feature.review.presentation.ReviewDetailsUiState
import com.elhady.movies.feature.review.presentation.ReviewUiState


data class MovieDetailsUiState(
    val id: Int = 0,
    val movieUiState: UpperUiState = UpperUiState(),
    val recommendedUiState: List<MediaVerticalUIState> = emptyList(),
    val castUiState: List<PeopleUIState> = emptyList(),
    val reviewUiState: List<ReviewUiState> = emptyList(),
    val reviewsDetails: ReviewDetailsUiState = ReviewDetailsUiState(),
    val onErrors: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val isLogin: Boolean = false,
    val userRating: Float = 5f,
    val userLists: List<UserListUi> = emptyList(),
    val userSelectedLists: List<Int> = emptyList()
){
    val isFailure: Boolean = onErrors.isNotEmpty()
}


data class UpperUiState(
    val id: Int = 0,
    val backdropPath: String = "",
    val genres: List<String> = emptyList(),
    val title: String = "",
    val overview: String = "",
    val voteAverage: Float = 0f,
    val videoKey: String = "",
    val isLogin: Boolean = false,
)
