package com.elhady.movies.feature.details.presentation.tvdetails

import com.elhady.movies.core.ui.base.UiText
import com.elhady.movies.core.ui.state.UserListUiState

sealed interface TvDetailsUiEffect {
    object NavigateBack : TvDetailsUiEffect
    data class NavigateToPersonDetails(val personId: Int) : TvDetailsUiEffect
    data class NavigateToSeasonDetails(val tvShowId: Int, val seasonNumber: Int) : TvDetailsUiEffect
    data class NavigateToTvDetails(val tvShowId: Int) : TvDetailsUiEffect
    object NavigateToShowMoreCast : TvDetailsUiEffect
    object NavigateToShowMoreRecommendation : TvDetailsUiEffect
    data class ShowRatingBottomSheet(val rating: Float) : TvDetailsUiEffect
    data class ShowSaveToListBottomSheet(val lists: List<UserListUiState>, val selectedLists: List<Int>) : TvDetailsUiEffect
    data class ShowSnackBar(val message: UiText) : TvDetailsUiEffect
    object CloseBottomSheet : TvDetailsUiEffect

}