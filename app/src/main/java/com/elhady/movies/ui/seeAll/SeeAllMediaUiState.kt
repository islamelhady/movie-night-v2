package com.elhady.movies.ui.seeAll

import androidx.paging.PagingData
import com.elhady.movies.ui.models.MediaUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SeeAllMediaUiState(
    val allMedia: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val isLoading: Boolean = false,
    val onErrors: List<String> = emptyList()
){
    val isError: Boolean
        get() = onErrors.isNotEmpty()
}