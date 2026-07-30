package com.elhady.movies.feature.search.presentation

import com.elhady.movies.core.ui.state.MovieHorizontalUiState
import com.elhady.movies.core.ui.state.PeopleUiState

sealed class SearchItem(val type: SearchItemType){
    data class MediaItem(val movieHorizontalUiState: MovieHorizontalUiState): SearchItem(SearchItemType.MEDIA)
    data class PeopleItem(val peopleItem: PeopleUiState): SearchItem(SearchItemType.PEOPLE)
}
enum class SearchItemType{
    MEDIA,
    PEOPLE
}

