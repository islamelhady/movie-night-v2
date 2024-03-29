package com.elhady.movies.ui.search

import androidx.paging.PagingData
import com.elhady.movies.ui.models.MediaUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SearchUiState(
    val inputSearch: String = "",
    val moviesSearchResult: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val searchHistoryResult: List<SearchHistoryUiState> = emptyList(),
    val mediaType: SearchType = SearchType.MOVIES,
    val isLoading: Boolean = false,
    val onErrors: List<String> = emptyList()
)

data class SearchHistoryUiState(val name: String)
enum class SearchType{
    MOVIES,
    TV,
    PEOPLE
}
