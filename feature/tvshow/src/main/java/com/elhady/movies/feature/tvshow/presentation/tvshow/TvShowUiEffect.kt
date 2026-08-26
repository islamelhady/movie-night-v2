package com.elhady.movies.feature.tvshow.presentation.tvshow

sealed interface TvShowUiEffect {
    data class NavigateToTvShowDetails(val tvId: Int) : TvShowUiEffect
    data class ShowSnackBar(val messages: String) : TvShowUiEffect
    object ScrollToTop : TvShowUiEffect
}