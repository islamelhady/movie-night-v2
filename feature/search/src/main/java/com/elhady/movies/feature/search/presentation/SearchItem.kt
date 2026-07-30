package com.elhady.movies.feature.search.presentation

import com.elhady.movies.core.ui.state.MovieHorizontalUIState
import com.elhady.movies.core.ui.state.PeopleUIState

sealed class SearchItem(val type: SearchItemType){
    data class MediaItem(val movieHorizontalUIState: MovieHorizontalUIState): SearchItem(SearchItemType.MEDIA)
    data class PeopleItem(val peopleItem: PeopleUIState): SearchItem(SearchItemType.PEOPLE)
}
enum class SearchItemType{
    MEDIA,
    PEOPLE
}

