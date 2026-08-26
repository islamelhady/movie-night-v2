package com.elhady.movies.feature.search.presentation.search

sealed interface SearchUiEffect {
    object NavigateBack : SearchUiEffect
    data class NavigateToMovieDetails(val id: Int) : SearchUiEffect
    data class NavigateToTvDetails(val id: Int) : SearchUiEffect
    data class NavigateToPeopleDetails(val id: Int) : SearchUiEffect
    data class ShowSnackBar(val message: String) : SearchUiEffect
    object OpenFilterBottomSheet : SearchUiEffect
}
