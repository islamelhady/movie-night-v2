package com.elhady.movies.feature.showmore.presentation.showmore

import com.elhady.movies.core.ui.base.UiText

sealed interface ShowMoreUiEffect {
    object NavigateBack : ShowMoreUiEffect
    data class NavigateToMovieDetails(val id: Int) : ShowMoreUiEffect
    data class NavigateToTvShowDetails(val id: Int) : ShowMoreUiEffect
    data class ShowSnackBar(val message: UiText) : ShowMoreUiEffect
}
