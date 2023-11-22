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
class FavoriteViewModel @Inject constructor(
    private val createListUseCase: CreateListUseCase,
    private val getCreatedListUseCase: GetCreatedListUseCase,
    private val createdListUiMapper: CreatedListUiMapper
) : BaseViewModel<FavCreatedListUiState>(FavCreatedListUiState()), CreatedListInteractionListener {

    private val _uiState = MutableStateFlow(FavCreatedListUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableStateFlow<Event<FavouriteUiEvent>?>(null)
    val uiEvent = _uiEvent.asStateFlow()

    init {
        getData()
    }

    override fun getData() {
        getCreatedList()
    }

    private fun getCreatedList(){
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = getCreatedListUseCase().map {
                createdListUiMapper.map(it)
            }
            _uiState.update {
                it.copy(createdList = result, isLoading = false)
            }
        }
    }

    fun onClickCreateList() {
        viewModelScope.launch {
            val result = createListUseCase(_uiState.value.dialogUiState.listName).map {
                createdListUiMapper.map(it)
            }
            _uiState.update { it.copy(createdList = result, isLoading = false) }
        }
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
        _uiState.update { it.copy(dialogUiState = CreateListDialogUiState(listName = listName.toString())) }
    }

    override fun onListClick(item: CreatedListUiState) {
        _uiEvent.update {
            Event(FavouriteUiEvent.ClickSelectedItemEvent(item))
        }
    }
}