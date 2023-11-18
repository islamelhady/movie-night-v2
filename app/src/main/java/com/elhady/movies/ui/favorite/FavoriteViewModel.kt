package com.elhady.movies.ui.favorite

import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.favList.CreateListUseCase
import com.elhady.movies.domain.usecases.favList.GetCreatedListUseCase
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
    private val createListUseCase: CreateListUseCase,
    private val getCreatedListUseCase: GetCreatedListUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(CreateListDialogUiState())
    val uiState = _uiState.asStateFlow()

    private val _createdListUiState = MutableStateFlow(FavCreatedListUiState())
    val createdListUIState = _createdListUiState.asStateFlow()

    private val _uiEvent = MutableStateFlow<Event<FavouriteUiEvent>?>(null)
    val uiEvent = _uiEvent.asStateFlow()

    override fun getData() {
        _createdListUiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = getCreatedListUseCase()
            _createdListUiState.update {
                it.copy(createdList = result)
            }

        }
    }

    fun createList(){
        viewModelScope.launch {
            createListUseCase(_uiState.value.listName)
        }
        onClickCreateList()
    }

    fun onClickAddList() {
        _uiEvent.update {
            Event(FavouriteUiEvent.CLickAddEvent)
        }
    }

    private fun onClickCreateList(){
        _uiEvent.update {
            Event(FavouriteUiEvent.ClickCreateEvent)
        }
    }

    fun onInputListNameChange(listName: CharSequence){
        _uiState.update { it.copy(listName = listName.toString()) }
    }
}