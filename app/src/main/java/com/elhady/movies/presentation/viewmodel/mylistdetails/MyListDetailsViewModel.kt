package com.elhady.movies.presentation.viewmodel.mylistdetails


import androidx.lifecycle.SavedStateHandle
import com.elhady.movies.core.bases.BaseViewModel
import com.elhady.movies.core.bases.ListName
import com.elhady.movies.core.bases.ListType
import com.elhady.movies.core.bases.StringsRes
import com.elhady.movies.core.domain.entities.StatusEntity
import com.elhady.movies.core.domain.usecase.common.AddToFavouriteUseCase
import com.elhady.movies.core.domain.usecase.common.AddToWatchList
import com.elhady.movies.core.domain.usecase.mylist.DeleteMovieFromDetailsListUseCase
import com.elhady.movies.core.domain.usecase.mylist.GetMyFavoriteListUseCase
import com.elhady.movies.core.domain.usecase.mylist.GetMyListDetailsByListIdUseCase
import com.elhady.movies.core.domain.usecase.mylist.GetMyWatchlistListUseCase
import com.elhady.movies.core.data.NoNetworkThrowable
import com.elhady.movies.presentation.viewmodel.mylistdetails.mapper.MyListDetailsUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class MyListDetailsViewModel @Inject constructor(
    private val stringsRes: StringsRes,
    private val getFavoriteUseCase: GetMyFavoriteListUseCase,
    private val getWatchlistUseCase: GetMyWatchlistListUseCase,
    private val getMovieListDetailsUseCase: GetMyListDetailsByListIdUseCase,
    private val deleteFavoriteUseCase: AddToFavouriteUseCase,
    private val deleteMovieFromDetailsListUseCase: DeleteMovieFromDetailsListUseCase,
    private val deleteWatchlistUseCase: AddToWatchList,
    private val myListDetailsUiMapper: MyListDetailsUiMapper,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel<MyListDetailsUiState, MyListDetailsUiEvent>(MyListDetailsUiState()),
    MyListDetailsListener {

    private val listType = savedStateHandle.get<String>("listType") ?: ""
    private val _listName = savedStateHandle.get<String>("listName") ?: ""
    val listName = when (_listName) {
        "watchlist" -> stringsRes.watchlist
        "favorite" -> stringsRes.favourite
        else -> _listName
    }
    private val listId = savedStateHandle.get<Int>("listId") ?: 0

    init {
        getData()
    }

    fun getData() {
        when (_listName) {
            ListName.FAVORITE.name -> {
                getAllFavorite()
            }

            ListName.WATCHLIST.name -> {
                getAllWatchlist()
            }

            else -> {
                getAllMovieListDetails(listId)
            }
        }
    }

    private fun getAllFavorite() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            call = { getFavoriteUseCase().map { myListDetailsUiMapper.map(it) } },
            onSuccess = ::onGetAllMoviesSuccess,
            onError = ::onError,
        )
    }

    private fun getAllWatchlist() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            call = { getWatchlistUseCase().map { myListDetailsUiMapper.map(it) } },
            onSuccess = ::onGetAllMoviesSuccess,
            onError = ::onError,
        )
    }

    private fun getAllMovieListDetails(listId: Int) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            call = { getMovieListDetailsUseCase(listId).map { myListDetailsUiMapper.map(it) } },
            onSuccess = ::onGetAllMoviesSuccess,
            onError = ::onError,
        )
    }

    private fun onGetAllMoviesSuccess(items: List<MovieUiState>) {
        _state.update {
            it.copy(
                movies = items,
                isLoading = false,
                error = null,
            )
        }
    }


    fun deleteMedia(position: Int) {
        val mediaId = state.value.movies[position].id
        val mediaType = state.value.movies[position].mediaType

        _state.update {
            it.copy(
                isLoading = true,
            )
        }
        when (_listName) {
            ListName.FAVORITE.name -> {
                deleteFavorite(mediaId , mediaType)
            }

            ListName.WATCHLIST.name -> {
                deleteWatchlist(mediaId, mediaType)
            }

            else -> {
                deleteMovieFromListDetails(mediaId)
            }
        }
    }

    private fun deleteFavorite(mediaId: Int  , mediaType: String ) {
        tryToExecute(
            call = { deleteFavoriteUseCase(mediaId, mediaType, false) },
            onSuccess = ::onDeleteMediaSuccess,
            onError = ::onError,
        )
    }

    private fun deleteWatchlist(mediaId: Int , mediaType: String  ) {
        tryToExecute(
            call = { deleteWatchlistUseCase(mediaId, mediaType, false) },
            onSuccess = ::onDeleteMediaSuccess,
            onError = ::onError,
        )
    }

    private fun deleteMovieFromListDetails(mediaId: Int) {
        tryToExecute(
            call = {
                deleteMovieFromDetailsListUseCase(listId = listId, mediaId = mediaId)
            },
            onSuccess = ::onDeleteMediaSuccess,
            onError = ::onError,
        )
    }


    private fun onDeleteMediaSuccess(isDelete: StatusEntity) {
        _state.update { it.copy(isLoading = false) }
        when (_listName) {
            ListName.FAVORITE.name -> {
                getAllFavorite()
            }

            ListName.WATCHLIST.name -> {
                getAllWatchlist()
            }

            else -> {
                getAllMovieListDetails(listId)
            }
        }
    }


    private fun onError(throwable: Throwable) {
        if (throwable == NoNetworkThrowable()) {
            showErrorWithSnackBar(throwable.message ?: "No Network Connection")
        } else if (throwable == SocketTimeoutException()) {
            showErrorWithSnackBar(throwable.message ?: "time out!")
        }
        _state.update {
            it.copy(
                error = listOf(throwable.message ?: "No Network Connection"),
                isLoading = false
            )
        }
    }

    private fun showErrorWithSnackBar(messages: String) {
        sendEvent(MyListDetailsUiEvent.ShowSnackBar(messages))
    }


    override fun onClickItem(itemId: Int , mediaType:String) {
        when(mediaType){
             ListType.MOVIE.name ->{
                 sendEvent(
                     MyListDetailsUiEvent.NavigateToMovieDetails(itemId)
                 )
            }
            ListType.TV.name ->{
                sendEvent(
                    MyListDetailsUiEvent.NavigateToTvDetails(itemId)
                )
            }
        }

    }

    override fun onClickBackButton() {
        sendEvent(MyListDetailsUiEvent.OnClickBack)
    }

}