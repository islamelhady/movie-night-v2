package com.elhady.movies.feature.tvshow.presentation.tvshow

import com.elhady.movies.core.ui.base.UiText

sealed interface TvShowUiEffect {
    data class NavigateToTvShowDetails(val tvId: Int) : TvShowUiEffect
    data class ShowSnackBar(val message: UiText) : TvShowUiEffect
    object ScrollToTop : TvShowUiEffect
}