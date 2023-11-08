package com.elhady.movies.ui.search

import androidx.paging.PagingData
import com.elhady.movies.ui.models.MediaUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SearchUiState(
    val inputSearch: String = "",
    val moviesSearchResult: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val isLoading: Boolean = false,
    val error: String = ""
)