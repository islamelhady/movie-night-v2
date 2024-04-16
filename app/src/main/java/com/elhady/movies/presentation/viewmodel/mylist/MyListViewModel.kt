package com.elhady.movies.presentation.viewmodel.mylist

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.viewModelScope
import com.elhady.movies.core.bases.BaseViewModel
import com.elhady.movies.core.bases.StringsRes
import com.elhady.movies.core.domain.entities.StatusEntity
import com.elhady.movies.core.domain.usecase.usecase.mylist.CreateListUseCase
import com.elhady.movies.core.domain.usecase.usecase.mylist.DeleteListUseCase
import com.elhady.movies.core.domain.usecase.usecase.mylist.GetListsCreatedUseCase
import com.elhady.movies.core.domain.usecase.repository.NoNetworkThrowable
import com.elhady.movies.presentation.viewmodel.mylist.mapper.MyListUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class MyListViewModel @Inject constructor(
    private val getMoviesUseCase: GetListsCreatedUseCase,
    private val deleteListUseCase: DeleteListUseCase,
    private val myListUiMapper: MyListUiMapper,
    private val createList: CreateListUseCase,
    private val stringsRes: StringsRes
) : BaseViewModel<MyListUiState, MyListUiEvent>(MyListUiState()), MyListListener, Parcelable {

    init {
        getData()
    }

    constructor(parcel: Parcel) : this(
        TODO("getMoviesUseCase"),
        TODO("deleteListUseCase"),
        TODO("myListUiMapper"),
        TODO("createList"),
        TODO("stringsRes")
    )

    fun getData() {
        _state.update { it.copy(isLoading = true ,) }
        tryToExecute(
            call = { getMoviesUseCase()},
            mapper = myListUiMapper,
            onSuccess = ::onGetAllListSuccess,
            onError = ::onError,
        )
    }

    private fun onGetAllListSuccess(items: List<ListMovieUiState>) {
        _state.update {
            it.copy(
                movieList = items,
                isLoading = false,
                error = null,
                isShowDelete = false
            )
        }
    }


    override fun onClickItem(listId: Int, listType: String, listName: String) {
        sendEvent(
            MyListUiEvent.NavigateToListDetails(
                listId = listId,
                listType = listType,
                listName = listName,
            )
        )
    }

    override fun onClickNewList() {
        viewModelScope.launch {
            _event.emit(MyListUiEvent.OpenCreateListBottomSheet)
        }
    }


    fun onCreateList(listName: String) {
        tryToExecute(
            call = {
                createList.invoke(listName)
            },
            onSuccess = ::onCreateUserNewListSuccess,
            onError = ::onError,
        )
    }

    private fun onCreateUserNewListSuccess(item: Boolean) {
        sendEvent(MyListUiEvent.ShowSnackBar(stringsRes.newListAddSuccessFully))
        getData()
    }



    override fun onClickShowDelete() {
       _state.update { it.copy(isShowDelete = true , error = null  ) }
    }

    override fun onClickDelete(listId: Int, listName: String) {
        sendEvent(MyListUiEvent.ShowConfirmDeleteDialog(listId, listName))
    }

    fun deleteList(listId: Int){
        tryToExecute(
            call = {
                deleteListUseCase.invoke(listId = listId)
            },
            onSuccess = ::onDeleteListSuccess,
            onError = ::onErrorDelete ,
        )
    }

    private fun onDeleteListSuccess(isDelete: StatusEntity) {
        _state.update { it.copy( isShowDelete = false) }
        getData()
    }


    private fun onError(throwable: Throwable) {
        if (throwable == NoNetworkThrowable()) {
            showErrorWithSnackBar(throwable.message ?: stringsRes.someThingErrorWhenAddRating)
        } else if (throwable == SocketTimeoutException()) {
            showErrorWithSnackBar(throwable.message ?: stringsRes.timeOut)
        }
        _state.update {
            it.copy(
                error = listOf(throwable.message ?: stringsRes.someThingErrorWhenAddRating),
                isLoading = false,
                isShowDelete = false
            )
        }
    }

    private fun onErrorDelete(throwable: Throwable) {
        _state.update {
            it.copy(
                error = listOf(throwable.message ?: "No Network Connection"),
                isLoading = false,
                isShowDelete = false
            )
        }
        getData()
    }

    private fun showErrorWithSnackBar(messages: String) {
        sendEvent(MyListUiEvent.ShowSnackBar(messages))
    }


    override fun onClickBackButton() {
        sendEvent(MyListUiEvent.OnClickBack)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyListViewModel> {
        override fun createFromParcel(parcel: Parcel): MyListViewModel {
            return MyListViewModel(parcel)
        }

        override fun newArray(size: Int): Array<MyListViewModel?> {
            return arrayOfNulls(size)
        }
    }

}