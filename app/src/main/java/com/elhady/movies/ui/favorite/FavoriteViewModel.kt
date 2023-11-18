package com.elhady.movies.ui.favorite

import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.favList.CreateListUseCase
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject  constructor(
    private val createListUseCase: CreateListUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(CreateListDialogUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableStateFlow<Event<FavouriteUiEvent>?>(null)
    val uiEvent = _uiEvent.asStateFlow()

    override fun getData() {

    }

    fun createList(){
        viewModelScope.launch {
            createListUseCase(_uiState.value.listName)
        }
    }

    fun onClickAddList() {
        _uiEvent.update {
            Event(FavouriteUiEvent.CLickAddEvent)
        }
    }

    fun onClickCreateList(){
        _uiEvent.update {
            Event(FavouriteUiEvent.ClickCreateEvent)
        }
    }

    fun onInputListNameChange(listName: CharSequence){
        _uiState.update { it.copy(listName = listName.toString()) }
    }
}