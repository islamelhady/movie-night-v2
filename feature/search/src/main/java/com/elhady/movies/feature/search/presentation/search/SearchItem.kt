package com.elhady.movies.feature.search.presentation.search

import com.elhady.movies.core.ui.state.MovieHorizontalUiState
import com.elhady.movies.core.ui.state.PeopleUiState

sealed interface SearchItem {
    data class MediaItem(val movieHorizontalUiState: MovieHorizontalUiState) : SearchItem
    data class PeopleItem(val peopleItem: PeopleUiState) : SearchItem
}
