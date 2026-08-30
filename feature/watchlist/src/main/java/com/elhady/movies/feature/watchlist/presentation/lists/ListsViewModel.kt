package com.elhady.movies.feature.watchlist.presentation.lists

import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.base.ErrorUiState
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.core.domain.usecase.account.CreateListUseCase
import com.elhady.movies.core.domain.usecase.account.DeleteListUseCase
import com.elhady.movies.core.domain.usecase.account.GetListsCreatedUseCase
import com.elhady.movies.core.ui.base.toErrorUiState
import com.elhady.movies.feature.watchlist.presentation.lists.mapper.ListsUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val getListsCreatedUseCase: GetListsCreatedUseCase,
    private val deleteListUseCase: DeleteListUseCase,
    private val createListUseCase: CreateListUseCase,
    private val listsUiMapper: ListsUiMapper,
    private val stringsRes: StringsRes,
) : BaseViewModel<ListsUiState, ListsUiEffect>(
    ListsUiState()
) {

    init {
        getData()
    }

    fun onEvent(event: ListsUiEvent) {
        when (event) {

            is ListsUiEvent.ListClicked -> {
                sendEffect(
                    ListsUiEffect.NavigateToListDetails(
                        listId = event.listId,
                        listType = event.listType,
                        listName = event.listName,
                    )
                )
            }

            ListsUiEvent.NewListClicked -> {
                sendEffect(
                    ListsUiEffect.OpenCreateListBottomSheet
                )
            }

            ListsUiEvent.BackClicked -> {
                sendEffect(ListsUiEffect.NavigateBack)
            }

            is ListsUiEvent.DeleteClicked -> {
                sendEffect(
                    ListsUiEffect.ShowDeleteConfirmation(
                        listId = event.listId,
                        listName = event.listName,
                    )
                )
            }

            is ListsUiEvent.CreateList -> {
                createList(event.listName)
            }

            ListsUiEvent.RetryClicked -> {
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
            mapper = listsUiMapper,
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
                    ListsUiEffect.ShowSnackBar(
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
        val errorUiState = error.toErrorUiState()
        _state.update {
            it.copy(
                isLoading = false,
                error = errorUiState,
            )
        }

        val message = when (errorUiState) {
            is ErrorUiState.NoNetwork -> stringsRes.noNetworkConnection
            is ErrorUiState.Timeout -> stringsRes.timeOut
            else -> stringsRes.someThingError
        }

        sendEffect(ListsUiEffect.ShowSnackBar(message))
    }
}
