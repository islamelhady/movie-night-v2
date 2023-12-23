package com.elhady.movies.ui.allMedia

import androidx.paging.PagingData
import com.elhady.movies.ui.models.MediaUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class AllMediaUiState(
    val allMedia: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val isLoading: Boolean = false,
    val onErrors: List<String> = emptyList()
)