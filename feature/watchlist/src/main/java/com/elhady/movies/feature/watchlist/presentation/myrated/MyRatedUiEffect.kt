package com.elhady.movies.feature.watchlist.presentation.myrated


sealed interface MyRatedUiEffect {

    object NavigateBack : MyRatedUiEffect

    data class NavigateToMovieDetails(
        val movieId: Int
    ) : MyRatedUiEffect

    data class NavigateToTvShowDetails(
        val tvShowId: Int
    ) : MyRatedUiEffect
}