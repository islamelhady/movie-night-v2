package com.elhady.movies.presentation.viewmodel.home

import com.elhady.movies.presentation.viewmodel.showmore.ShowMoreType

sealed interface HomeUiEvent{
    data class MovieEvent(val itemId: Int) : HomeUiEvent
    data class TvShowEvent(val itemId: Int) : HomeUiEvent

    data class ClickShowMoreEvent(val showMore: ShowMoreType) : HomeUiEvent

    data class ShowSnackBarEvent(val message: String): HomeUiEvent
}