package com.elhady.movies.ui.favorite

import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.mappers.favList.CreatedListMapper
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
class FavoriteViewModel @Inject constructor(
    private val createListUseCase: CreateListUseCase,
    private val getCreatedListUseCase: GetCreatedListUseCase,
    private val createdListUiMapper: CreatedListUiMapper
) : BaseViewModel(), CreatedListInteractionListener {

    private val _uiState = MutableStateFlow(CreateListDialogUiState())
    val uiState = _uiState.asStateFlow()

    private val _createdListUiState = MutableStateFlow(FavCreatedListUiState())
    val createdListUIState = _createdListUiState.asStateFlow()

    private val _uiEvent = MutableStateFlow<Event<FavouriteUiEvent>?>(null)
    val uiEvent = _uiEvent.asStateFlow()

    override fun getData() {
        getCreatedList()
    }

    private fun getCreatedList(){
        _createdListUiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = getCreatedListUseCase().map {
                createdListUiMapper.map(it)
            }
            _createdListUiState.update {
                it.copy(createdList = result, isLoading = false)
            }
        }
    }

    fun createList() {
        viewModelScope.launch {
            val result = createListUseCase(_uiState.value.listName).map {
                createdListUiMapper.map(it)
            }
            _createdListUiState.update { it.copy(createdList = result, isLoading = false) }
        }
        onClickCreateList()
    }

    private fun onClickCreateList() {
        _uiEvent.update {
            Event(FavouriteUiEvent.ClickCreateEvent)
        }
    }

    fun onClickAddList() {
        _uiEvent.update {
            Event(FavouriteUiEvent.CLickAddEvent)
        }
    }


    fun onInputListNameChange(listName: CharSequence) {
        _uiState.update { it.copy(listName = listName.toString()) }
    }

    override fun onListClick(item: CreatedListUiState) {
        TODO("Not yet implemented")
    }
}