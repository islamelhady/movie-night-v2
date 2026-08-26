package com.elhady.movies.feature.watchlist.presentation.listcontents

sealed interface ListContentsUiEffect {

    data class NavigateToMovieContents(
        val movieId: Int,
    ) : ListContentsUiEffect

    data class NavigateToTvShowContents(
        val tvShowId: Int,
    ) : ListContentsUiEffect

    object NavigateBack : ListContentsUiEffect

    data class ShowSnackBar(
        val message: String,
    ) : ListContentsUiEffect
}