package com.elhady.movies.ui.actors

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import com.elhady.movies.domain.models.Actor
import com.elhady.movies.domain.usecases.GetAllActorsUseCase
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.home.adapters.ActorInteractionListener
import com.elhady.movies.ui.mappers.ActorUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
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
        tryToExecute(
            call = { getAllActorsUseCase()},
            onSuccess = ::onSuccessActors,
            onError = ::onError
        )
    }

    private fun onSuccessActors(actors: Flow<PagingData<Actor>>){
        val result = actors.map {  pagingData->
            pagingData.map(actorUiMapper::map)
        }
        _state.update { it.copy(isLoading = false, actors = result, onErrors = emptyList()) }
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