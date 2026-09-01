package com.elhady.movies.feature.search.presentation.search

import com.elhady.movies.core.ui.base.UiText

sealed interface SearchUiEffect {
    object NavigateBack : SearchUiEffect
    data class NavigateToMovieDetails(val id: Int) : SearchUiEffect
    data class NavigateToTvDetails(val id: Int) : SearchUiEffect
    data class NavigateToPeopleDetails(val id: Int) : SearchUiEffect
    data class ShowSnackBar(val message: UiText) : SearchUiEffect
    object OpenFilterBottomSheet : SearchUiEffect
}
