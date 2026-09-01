package com.elhady.movies.feature.watchlist.presentation.ratedmedia

import com.elhady.movies.core.ui.base.UiText

sealed interface RatedMediaUiEffect {

    object NavigateBack : RatedMediaUiEffect

    data class NavigateToMovieDetails(
        val movieId: Int
    ) : RatedMediaUiEffect

    data class NavigateToTvShowDetails(
        val tvShowId: Int
    ) : RatedMediaUiEffect

    data class ShowSnackBar(
        val message: UiText
    ) : RatedMediaUiEffect
}
