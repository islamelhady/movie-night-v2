package com.elhady.movies.feature.explore.presentation.explore

import com.elhady.movies.core.ui.base.UiText

sealed interface ExploreUiEffect {
    object NavigateToSearch: ExploreUiEffect
    data class ShowSnackBar(val message: UiText): ExploreUiEffect
    data class NavigateToMovieDetails(val movieId: Int): ExploreUiEffect
}
