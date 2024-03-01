package com.elhady.viewmodel.actors

import androidx.paging.PagingData
import com.elhady.viewmodel.models.ActorUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class ActorsUiState (
    val actors: Flow<PagingData<ActorUiState>> = emptyFlow(),
    val isLoading: Boolean = false,
    val onErrors: List<String> = emptyList()
)