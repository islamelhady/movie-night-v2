package com.elhady.viewmodel.category

import androidx.paging.PagingData
import com.elhady.viewmodel.models.MediaUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class CategoryUiState(
    val categoryResult: List<CategoryGenreUiState> = emptyList(),
    val moviesResult: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val categorySelectedID: Int = FIRST_CATEGORY_ID,
    val isLoading: Boolean = false,
    val onErrors: List<String> = emptyList()
)
data class CategoryGenreUiState(
    val id: Int = FIRST_CATEGORY_ID,
    val name: String ="",
    val isSelected: Boolean = false
)