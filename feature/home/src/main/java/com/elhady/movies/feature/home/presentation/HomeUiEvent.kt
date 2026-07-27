package com.elhady.movies.feature.home.presentation

import com.elhady.movies.core.common.ShowMoreType

sealed interface HomeUiEvent {
    data class MovieEvent(val itemId: Int) : HomeUiEvent
    data class TvShowEvent(val itemId: Int) : HomeUiEvent
    data class ClickShowMoreEvent(val showMore: ShowMoreType) : HomeUiEvent
    data class ShowSnackBarEvent(val message: String) : HomeUiEvent
}
