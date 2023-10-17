package com.elhady.movies.ui.actors

import androidx.paging.PagingData
import com.elhady.movies.ui.models.ActorUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class ActorsUiState (
    val actors: Flow<PagingData<ActorUiState>> = emptyFlow(),
    val isLoading: Boolean = false,
    val error: List<Error> = emptyList()
)