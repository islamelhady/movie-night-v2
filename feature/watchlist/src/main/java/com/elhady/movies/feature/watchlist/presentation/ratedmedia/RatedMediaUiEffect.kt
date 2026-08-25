package com.elhady.movies.feature.watchlist.presentation.ratedmedia


sealed interface RatedMediaUiEffect {

    object NavigateBack : RatedMediaUiEffect

    data class NavigateToMovieDetails(
        val movieId: Int
    ) : RatedMediaUiEffect

    data class NavigateToTvShowDetails(
        val tvShowId: Int
    ) : RatedMediaUiEffect
}