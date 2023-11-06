package com.elhady.movies.ui.category

import androidx.paging.PagingData
import com.elhady.movies.ui.models.MediaUiState
import com.elhady.movies.ui.movieDetails.ErrorUiState
import com.elhady.movies.utilities.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class CategoryUiState(
    val categoryGenreResult: List<CategoryGenreUiState> = emptyList(),
    val moviesResult: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val categorySelectedID: Int = Constants.All,
    val isLoading: Boolean = false,
    val error: List<ErrorUiState> = emptyList()
)