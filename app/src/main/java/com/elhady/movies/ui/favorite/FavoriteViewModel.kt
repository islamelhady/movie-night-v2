package com.elhady.movies.ui.favorite

import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.CheckIfLoggedInUseCase
import com.elhady.movies.domain.usecases.favList.CreateListUseCase
import com.elhady.movies.domain.usecases.favList.GetFavouriteCreatedListUseCase
import com.elhady.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val createListUseCase: CreateListUseCase,
    private val getFavouriteCreatedListUseCase: GetFavouriteCreatedListUseCase,
    private val checkIfLoggedInUseCase: CheckIfLoggedInUseCase,
    private val createdListUiMapper: CreatedListUiMapper
) : BaseViewModel<FavCreatedListUiState, FavouriteUiEvent>(FavCreatedListUiState()), CreatedListInteractionListener {

    init {
        getData()
    }

    override fun getData() {
        if (checkIfLoggedInUseCase()){
            tryToExecute(
                call = { getFavouriteCreatedListUseCase() },
                onSuccess = ::onSuccessCreatedList,
                onError = ::onErrors,
                mapper = createdListUiMapper
            )
        }else{
            _state.update { it.copy(isLoggedIn = false) }
        }
    }

    private fun onSuccessCreatedList(createdList: List<CreatedListUiState>) {
        _state.update { it.copy(createdList = createdList, isLoading = false, isLoggedIn = true, onErrors = emptyList()) }
    }

    private fun onErrors(throwable: Throwable){
        val errors = _state.value.onErrors.toMutableList()
        errors.add(throwable.message.toString())
        _state.update { it.copy(onErrors = errors, isLoading = false) }
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

    fun onClickLogin(){
        sendEvent(FavouriteUiEvent.ClickLogin)
    }
}