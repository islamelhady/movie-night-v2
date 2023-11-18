package com.elhady.movies.ui.favorite

import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.favList.CreateListUseCase
import com.elhady.movies.ui.base.BaseViewModel
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

    override fun getData() {

    }

    fun createList(){
        viewModelScope.launch {
            createListUseCase(_uiState.value.listName)
        }
    }

    fun onInputListNameChange(listName: CharSequence){
        _uiState.update { it.copy(listName = listName.toString()) }
    }
}