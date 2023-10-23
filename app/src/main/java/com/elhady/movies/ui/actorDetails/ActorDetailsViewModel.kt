package com.elhady.movies.ui.actorDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.GetActorDetailsUseCase
import com.elhady.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorDetailsViewModel @Inject constructor(private val getActorDetailsUseCase: GetActorDetailsUseCase) : BaseViewModel() {


    private val _uIState = MutableStateFlow(ActorDetailsUiState())
    val uiState = _uIState.asStateFlow()

    override fun getData() {
        TODO("Not yet implemented")
    }

    fun getActorDetails(actorID: Int){
        viewModelScope.launch {
            getActorDetailsUseCase(actorID)
        }
    }


}