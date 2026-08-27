package com.elhady.movies.feature.details.presentation.moviedetails

import com.elhady.movies.core.ui.state.MediaVerticalUiState
import com.elhady.movies.core.ui.state.PeopleUiState
import com.elhady.movies.core.ui.state.UserListUiState


data class MovieDetailsUiState(
    val id: Int = 0,
    val movieUiState: UpperUiState = UpperUiState(),
    val recommendedUiState: List<MediaVerticalUiState> = emptyList(),
    val castUiState: List<PeopleUiState> = emptyList(),
    val reviewUiState: List<ReviewUiState> = emptyList(),
    val reviewsDetails: ReviewDetailsUiState = ReviewDetailsUiState(),
    val onErrors: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val isLogin: Boolean = false,
    val userRating: Float = 5f,
    val userLists: List<UserListUiState> = emptyList(),
    val userSelectedLists: List<Int> = emptyList(),
    val isPlayerVisible: Boolean = false
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
