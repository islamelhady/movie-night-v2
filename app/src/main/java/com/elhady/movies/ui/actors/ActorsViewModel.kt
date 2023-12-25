package com.elhady.movies.ui.actors

import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.elhady.movies.domain.usecases.GetAllActorsUseCase
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.home.adapters.ActorInteractionListener
import com.elhady.movies.ui.mappers.ActorUiMapper
import com.elhady.movies.ui.models.ActorUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException
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
        _state.update { it.copy(isLoading = true, onErrors = emptyList()) }
        getAllActors()
    }


    private fun getAllActors() {
        try {
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
                        onErrors = emptyList()
                    )
                }
            }
        }catch (th: UnknownHostException){
            onError(th)
        }
    }

    private fun onError(th: Throwable){
        val errors = _state.value.onErrors.toMutableList()
        errors.add(th.message.toString())
        _state.update { it.copy(onErrors = errors, isLoading = false) }
    }

    fun setErrorUiState(combinedLoadStates: CombinedLoadStates) {
        when (combinedLoadStates.refresh) {
            is LoadState.NotLoading -> {
                _state.update {
                    it.copy(isLoading = false, onErrors = emptyList())
                }
            }

            is LoadState.Loading -> {
                _state.update {
                    it.copy(isLoading = true, onErrors = emptyList())
                }
            }

            is LoadState.Error -> {
                _state.update {
                    it.copy(isLoading = false, onErrors = listOf(("No Network Connection")))
                }
            }
        }
    }

    override fun onClickActor(actorID: Int) {
        sendEvent(ActorsUiEvent.ClickActorEvent(actorID))
    }


}