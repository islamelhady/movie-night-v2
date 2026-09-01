package com.elhady.movies.feature.watchlist.presentation.listcontents

import com.elhady.movies.core.ui.base.UiText

sealed interface ListContentsUiEffect {

    data class NavigateToMovieContents(
        val movieId: Int,
    ) : ListContentsUiEffect

    data class NavigateToTvShowContents(
        val tvShowId: Int,
    ) : ListContentsUiEffect

    object NavigateBack : ListContentsUiEffect

    data class ShowSnackBar(
        val message: UiText,
    ) : ListContentsUiEffect
}