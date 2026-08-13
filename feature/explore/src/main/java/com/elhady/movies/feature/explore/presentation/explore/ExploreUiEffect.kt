package com.elhady.movies.feature.explore.presentation.explore

sealed interface ExploreUiEffect {
    object NavigateToSearch: ExploreUiEffect
    data class ShowSnackBar(val message: String): ExploreUiEffect
    data class NavigateToMovieDetails(val movieId: Int): ExploreUiEffect
}
