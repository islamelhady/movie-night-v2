package com.elhady.movies.ui.actors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.elhady.movies.domain.usecases.GetAllActorsUseCase
import com.elhady.movies.ui.mappers.ActorUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorsViewModel @Inject constructor(
    private val getAllActorsUseCase: GetAllActorsUseCase,
    private val actorUiMapper: ActorUiMapper
) : ViewModel() {

    private val _actorsUiState = MutableStateFlow(ActorsUiState())
    val actorsUiState = _actorsUiState.asStateFlow()

    init {
        getAllActors()
    }
    private fun getAllActors() {
        viewModelScope.launch {
            val items = getAllActorsUseCase().map { paging ->
                paging.map {
                    actorUiMapper.map(it)
                }
            }
            _actorsUiState.update {
                it.copy(
                    actors = items,
                    isLoading = false,
                    error = emptyList()
                )
            }
        }
    }


}