package com.elhady.movies.feature.home.presentation.home

import com.elhady.movies.core.common.ShowMoreType

sealed interface HomeUiEvent {
    data class MovieClicked(
        val movieId: Int
    ) : HomeUiEvent

    data class TvShowClicked(
        val tvShowId: Int
    ) : HomeUiEvent

    data class PeopleClicked(
        val personId: Int
    ) : HomeUiEvent

    data class ShowMoreClicked(
        val type: ShowMoreType
    ) : HomeUiEvent
}
