package com.elhady.viewmodel.favorite

import androidx.lifecycle.viewModelScope
import com.elhady.base.BaseViewModel
import com.elhady.usecase.CheckIfLoggedInUseCase
import com.elhady.usecase.favList.CreateListUseCase
import com.elhady.usecase.favList.DeleteListUseCase
import com.elhady.usecase.favList.GetFavouriteCreatedListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val createListUseCase: CreateListUseCase,
    private val getFavouriteCreatedListUseCase: GetFavouriteCreatedListUseCase,
    private val checkIfLoggedInUseCase: CheckIfLoggedInUseCase,
    private val deleteListUseCase: DeleteListUseCase,
    private val createdListUiMapper: CreatedListUiMapper
) : BaseViewModel<FavCreatedListUiState, FavouriteUiEvent>(FavCreatedListUiState()), CreatedListInteractionListener {

    init {
        _state.update { it.copy(isLoading = true) }
        getData()
    }

    override fun getData() {
        if (checkIfLoggedInUseCase()){
            getFavouriteList()
        }else{
            _state.update { it.copy(isLoggedIn = false, isLoading = false, onErrors = emptyList()) }
        }
    }

    private fun getFavouriteList(){
        tryToExecute(
            call = { getFavouriteCreatedListUseCase() },
            onSuccess = ::onSuccessCreatedList,
            onError = ::onErrors,
            mapper = createdListUiMapper
        )
    }

    private fun onSuccessCreatedList(createdList: List<CreatedListUiState>) {
        _state.update { it.copy(createdList = createdList, isLoading = false, isLoggedIn = true, onErrors = emptyList()) }
    }

    private fun onErrors(throwable: Throwable){
        _state.update { it.copy(onErrors = listOf(throwable.message.toString()), isLoading = false) }
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
        sendEvent(FavouriteUiEvent.ClickSelectedItemEvent(id = item.id, listName = item.name))
    }

    override fun onClickDeleteList(id: Int) {
        tryToExecute(
            call = {deleteListUseCase(id)},
            onSuccess = ::onSuccessDeleteList,
            onError = ::onErrors,
        )
    }

    private fun onSuccessDeleteList(statue: StatusResponse){
        sendEvent(FavouriteUiEvent.ShowSnackBar("${statue.statusMessage} ${statue.success}"))
        getFavouriteList()
    }

    fun onClickLogin(){
        sendEvent(FavouriteUiEvent.ClickLogin)
    }
}