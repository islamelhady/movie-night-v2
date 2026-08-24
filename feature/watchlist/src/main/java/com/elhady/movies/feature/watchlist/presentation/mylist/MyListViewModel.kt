package com.elhady.movies.feature.watchlist.presentation.mylist

import androidx.lifecycle.viewModelScope
import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.core.domain.model.common.Status
import com.elhady.movies.core.domain.usecase.account.CreateListUseCase
import com.elhady.movies.core.domain.usecase.account.DeleteListUseCase
import com.elhady.movies.core.domain.usecase.account.GetListsCreatedUseCase
import com.elhady.movies.core.common.NoNetworkThrowable
import com.elhady.movies.core.ui.base.messageRes
import com.elhady.movies.core.ui.base.toErrorUiState
import com.elhady.movies.feature.watchlist.presentation.mylist.mapper.MyListUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class MyListViewModel @Inject constructor(
    private val getListsCreatedUseCase: GetListsCreatedUseCase,
    private val deleteListUseCase: DeleteListUseCase,
    private val createListUseCase: CreateListUseCase,
    private val myListUiMapper: MyListUiMapper,
    private val stringsRes: StringsRes,
) : BaseViewModel<MyListUiState, MyListUiEffect>(
    MyListUiState()
) {

    init {
        getData()
    }

    fun onEvent(event: MyListUiEvent) {
        when (event) {

            is MyListUiEvent.ListClicked -> {
                sendEffect(
                    MyListUiEffect.NavigateToListDetails(
                        listId = event.listId,
                        listType = event.listType,
                        listName = event.listName,
                    )
                )
            }

            MyListUiEvent.NewListClicked -> {
                sendEffect(
                    MyListUiEffect.OpenCreateListBottomSheet
                )
            }

            MyListUiEvent.BackClicked -> {
                sendEffect(MyListUiEffect.NavigateBack)
            }

            is MyListUiEvent.DeleteClicked -> {
                sendEffect(
                    MyListUiEffect.ShowDeleteConfirmation(
                        listId = event.listId,
                        listName = event.listName,
                    )
                )
            }

            is MyListUiEvent.CreateList -> {
                createList(event.listName)
            }

            MyListUiEvent.RetryClicked -> {
                getData()
            }
        }
    }

    private fun getData() {
        _state.update {
            it.copy(
                isLoading = true,
                error = null,
            )
        }

        tryToExecute(
            call = {
                getListsCreatedUseCase()
            },
            mapper = myListUiMapper,
            onSuccess = ::onGetListsSuccess,
            onError = ::onError,
        )
    }

    private fun onGetListsSuccess(
        lists: List<ListMovieUiState>
    ) {
        _state.update {
            it.copy(
                movieLists = lists,
                isLoading = false,
                error = null,
            )
        }
    }

    private fun createList(listName: String) {
        tryToExecute(
            call = {
                createListUseCase(listName)
            },
            onSuccess = {
                sendEffect(
                    MyListUiEffect.ShowSnackBar(
                        stringsRes.newListAddSuccessFully
                    )
                )

                getData()
            },
            onError = ::onError,
        )
    }

    fun deleteList(listId: Int) {
        tryToExecute(
            call = {
                deleteListUseCase(listId)
            },
            onSuccess = {
                getData()
            },
            onError = ::onError,
        )
    }

    private fun onError(error: AppException) {
        _state.update {
            it.copy(
                isLoading = false,
                error = error.toErrorUiState(),
            )
        }

        sendEffect(
            MyListUiEffect.ShowSnackBar(
                error.toErrorUiState().toString()
            )
        )
    }
}
