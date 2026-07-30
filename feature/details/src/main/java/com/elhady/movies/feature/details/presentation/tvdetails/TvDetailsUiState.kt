package com.elhady.movies.feature.details.presentation.tvdetails

import com.elhady.movies.feature.details.presentation.episodedetails.CommentUiState
import com.elhady.movies.core.ui.state.MediaVerticalUiState
import com.elhady.movies.core.ui.state.PeopleUiState
import com.elhady.movies.feature.details.presentation.seasondetails.SeasonHorizontalUiState
import com.elhady.movies.core.ui.state.UserListUiState

data class TvDetailsUiState(
    val id: Int = 0,
    val info: Info = Info(),
    val cast: List<PeopleUiState> = emptyList(),
    val recommended: List<MediaVerticalUiState> = emptyList(),
    val seasons: List<SeasonHorizontalUiState> = emptyList(),
    val onErrors: List<String> = emptyList(),
    val isLoading: Boolean = true,
    val reviews: List<CommentUiState> = emptyList(),
    val ratingSuccess: String = "",
    val userRating: Float = 0.0f,
    val youtubeKeyId: String = "",
    val userLists: List<UserListUiState> = emptyList(),
    val userSelectedLists: List<Int> = emptyList(),
    val isLogin: Boolean = false,
) {
    val reviewsIsEmpty: Boolean get() = reviews.isEmpty()
    val isFailure: Boolean
        get() =
            onErrors.isNotEmpty()

    data class Info(
        val backdropImageUrl: String = "",
        val name: String = "",
        val rating: Float = 0.0f,
        val description: String = "",
        val genres: List<String> = emptyList(),
        val isLogin: Boolean = false
    )

}
