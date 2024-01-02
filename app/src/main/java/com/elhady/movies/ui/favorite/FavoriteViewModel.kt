package com.elhady.movies.ui.favorite

import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.favList.CreateListUseCase
import com.elhady.movies.domain.usecases.favList.GetCreatedListUseCase
import com.elhady.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val createListUseCase: CreateListUseCase,
    private val getCreatedListUseCase: GetCreatedListUseCase,
    private val createdListUiMapper: CreatedListUiMapper
) : BaseViewModel<FavCreatedListUiState, FavouriteUiEvent>(FavCreatedListUiState()), CreatedListInteractionListener {

    init {
        getData()
    }

    override fun getData() {
        getCreatedList()
    }

    private fun getCreatedList(){
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = getCreatedListUseCase().map {
                createdListUiMapper.map(it)
            }
            _state.update {
                it.copy(createdList = result, isLoading = false)
            }
        }
    }

    fun onClickCreateList() {
        viewModelScope.launch {
            val result = createListUseCase(_state.value.dialogUiState.listName).map {
                createdListUiMapper.map(it)
            }
            _state.update { it.copy(createdList = result, isLoading = false) }
        }
        sendEvent(FavouriteUiEvent.ClickCreateEvent)
    }

    fun onClickAddList() {
        sendEvent(FavouriteUiEvent.CLickAddEvent)
    }


    fun onInputListNameChange(listName: CharSequence) {
        _state.update { it.copy(dialogUiState = CreateListDialogUiState(listName = listName.toString())) }
    }

    override fun onListClick(item: CreatedListUiState) {
        sendEvent(FavouriteUiEvent.ClickSelectedItemEvent(item))
    }
}