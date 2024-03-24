package com.elhady.movies.presentation.viewmodel.search

import com.elhady.movies.presentation.viewmodel.common.model.MovieHorizontalUIState
import com.elhady.movies.presentation.viewmodel.common.model.PeopleUIState

sealed class SearchItem(val type: SearchItemType){
    data class MediaItem(val movieHorizontalUIState: MovieHorizontalUIState): SearchItem(SearchItemType.MEDIA)
    data class PeopleItem(val peopleItem: PeopleUIState): SearchItem(SearchItemType.PEOPLE)
}
enum class SearchItemType{
    MEDIA,
    PEOPLE
}

