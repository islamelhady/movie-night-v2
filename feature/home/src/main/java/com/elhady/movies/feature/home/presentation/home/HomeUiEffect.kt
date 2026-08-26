package com.elhady.movies.feature.home.presentation.home

import com.elhady.movies.core.common.ShowMoreType

sealed interface HomeUiEffect {

    data class NavigateToMovieDetails(
        val movieId: Int
    ) : HomeUiEffect

    data class NavigateToTvShowDetails(
        val tvShowId: Int
    ) : HomeUiEffect

    data class NavigateToPeopleDetails(
        val personId: Int
    ) : HomeUiEffect

    data class NavigateToShowMore(
        val type: ShowMoreType
    ) : HomeUiEffect

    data class ShowSnackBar(
        val messageRes: Int
    ) : HomeUiEffect
}
