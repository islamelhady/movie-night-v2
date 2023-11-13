package com.elhady.movies.ui.search

import androidx.paging.PagingData
import com.elhady.movies.ui.models.MediaUiState
import com.elhady.movies.ui.movieDetails.ErrorUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SearchUiState(
    val inputSearch: String = "",
    val moviesSearchResult: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val searchHistoryResult: List<SearchHistoryUiState> = emptyList(),
    val mediaType: MediaTypes = MediaTypes.MOVIES,
    val isLoading: Boolean = false,
    val error: List<ErrorUiState> = emptyList()
)