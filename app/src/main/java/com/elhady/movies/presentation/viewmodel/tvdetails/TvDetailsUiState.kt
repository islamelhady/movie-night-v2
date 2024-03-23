package com.elhady.movies.presentation.viewmodel.tvdetails

import com.elhady.movies.presentation.viewmodel.common.model.CommentUIState
import com.elhady.movies.presentation.viewmodel.common.model.MediaVerticalUIState
import com.elhady.movies.presentation.viewmodel.common.model.PeopleUIState
import com.elhady.movies.presentation.viewmodel.common.model.SeasonHorizontalUIState
import com.elhady.movies.presentation.viewmodel.common.model.UserListUi

data class TvDetailsUiState(
    val id: Int = 0,
    val info: Info = Info(),
    val cast: List<PeopleUIState> = emptyList(),
    val recommended: List<MediaVerticalUIState> = emptyList(),
    val seasons: List<SeasonHorizontalUIState> = emptyList(),
    val errors: List<String> = emptyList(),
    val isLoading: Boolean = true,
    val reviews: List<CommentUIState> = emptyList(),
    val ratingSuccess: String = "",
    val userRating: Float = 0.0f,
    val youtubeKeyId: String = "",
    val userLists: List<UserListUi> = emptyList(),
    val userSelectedLists: List<Int> = emptyList(),
    val isLogined: Boolean = false,
) {
    val reviewsIsEmpty : Boolean get() = reviews.isEmpty()
    val isFailure: Boolean get() =
        errors.isNotEmpty()

    data class Info(
        val backdropImageUrl: String = "",
        val name: String = "",
        val rating: Float = 0.0f,
        val description: String = "",
        val genres: List<String> = emptyList(),
        val isLogined: Boolean = false,
        )

}
