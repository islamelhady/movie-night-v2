package com.elhady.movies.ui.actors

import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.map
import com.elhady.movies.domain.usecases.GetAllActorsUseCase
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.home.adapters.ActorInteractionListener
import com.elhady.movies.ui.mappers.ActorUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorsViewModel @Inject constructor(
    private val getAllActorsUseCase: GetAllActorsUseCase,
    private val actorUiMapper: ActorUiMapper
) : BaseViewModel<ActorsUiState, ActorsUiEvent>(ActorsUiState()), ActorInteractionListener {

    init {
        getData()
    }

    override fun getData() {
        _state.update { it.copy(isLoading = true) }
        getAllActors()
    }


    private fun getAllActors() {
        viewModelScope.launch {
            val items = getAllActorsUseCase().map { paging ->
                paging.map {
                    actorUiMapper.map(it)
                }
            }
            _state.update {
                it.copy(
                    actors = items,
                    isLoading = false,
                    error = emptyList()
                )
            }
        }
    }

    fun setErrorUiState(combinedLoadStates: CombinedLoadStates) {
        when (combinedLoadStates.refresh) {
            is LoadState.NotLoading -> {
                _state.update {
                    it.copy(isLoading = false, error = emptyList())
                }
            }

            is LoadState.Loading -> {
                _state.update {
                    it.copy(isLoading = true, error = emptyList())
                }
            }

            is LoadState.Error -> {
                _state.update {
                    it.copy(isLoading = false, error = listOf(Error("")))
                }
            }
        }
    }

    override fun onClickActor(actorID: Int) {
        sendEvent(ActorsUiEvent.ClickActorEvent(actorID))
    }


}