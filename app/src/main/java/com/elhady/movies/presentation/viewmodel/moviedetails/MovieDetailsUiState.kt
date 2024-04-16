package com.elhady.movies.presentation.viewmodel.moviedetails

import com.elhady.movies.presentation.viewmodel.common.model.MediaVerticalUIState
import com.elhady.movies.presentation.viewmodel.common.model.PeopleUIState
import com.elhady.movies.presentation.viewmodel.common.model.UserListUi


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


data class ReviewUiState(
    val name: String?,
    val avatarPath: String?,
    val content: String?,
    val createdAt: String?,
)

data class ReviewDetailsUiState(
    val page: Int = 1,
    val totalPages: Int = 1,
    val totalReviews: Int = 1
)