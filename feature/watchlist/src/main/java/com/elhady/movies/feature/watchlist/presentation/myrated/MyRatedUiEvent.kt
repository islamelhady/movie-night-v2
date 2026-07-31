package com.elhady.movies.feature.watchlist.presentation.myrated


sealed interface MyRatedUiEvent{
    object NavigateBack: MyRatedUiEvent

    object ShowMyRatedMoviesPressed: MyRatedUiEvent

    object ShowMyRatedTvShowPressed: MyRatedUiEvent

    data class NavigateToTVShowDetails(val tvId: Int) : MyRatedUiEvent

    data class NavigateToMovieDetails(val movieId: Int) : MyRatedUiEvent
}
