package com.elhady.movies.presentation.viewmodel.myrated


sealed interface MyRatedEvents{
    object NavigateBack: MyRatedEvents

    object ShowMyRatedMoviesPressed: MyRatedEvents

    object ShowMyRatedTvShowPressed: MyRatedEvents

    data class NavigateToTVShowDetails(val tvId: Int) : MyRatedEvents

    data class NavigateToMovieDetails(val movieId: Int) : MyRatedEvents
}