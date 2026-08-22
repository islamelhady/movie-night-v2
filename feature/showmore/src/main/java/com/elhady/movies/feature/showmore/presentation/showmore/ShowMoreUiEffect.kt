package com.elhady.movies.feature.showmore.presentation.showmore

sealed interface ShowMoreUiEffect {
    object NavigateBack : ShowMoreUiEffect
    data class NavigateToMovieDetails(val id: Int) : ShowMoreUiEffect
    data class NavigateToTvShowDetails(val id: Int) : ShowMoreUiEffect
    data class ShowSnackBar(val message: String) : ShowMoreUiEffect
}
