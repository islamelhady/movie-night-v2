package com.elhady.movies.ui.actorDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.GetActorDetailsUseCase
import com.elhady.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorDetailsViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getActorDetailsUseCase: GetActorDetailsUseCase,
    private val actorDetailsUiMapper: ActorDetailsUiMapper
) : BaseViewModel() {

    private val args = ActorDetailsFragmentArgs.fromSavedStateHandle(state)

    private val _uIState = MutableStateFlow(ActorDetailsUiState())
    val uiState = _uIState.asStateFlow()

    init {
        getData()
    }
    override fun getData() {
        getActorDetails()
    }

    private fun getActorDetails() {
        viewModelScope.launch {
            val actorDetails = getActorDetailsUseCase(args.actorID)
            actorDetailsUiMapper.map(actorDetails)
            _uIState.update {
                it.copy(
                    id = actorDetails.id,
                    name = actorDetails.name
                )
            }
        }
    }


}