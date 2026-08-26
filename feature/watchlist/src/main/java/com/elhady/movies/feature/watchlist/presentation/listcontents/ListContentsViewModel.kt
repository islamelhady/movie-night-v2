package com.elhady.movies.feature.watchlist.presentation.listcontents


import androidx.lifecycle.SavedStateHandle
import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.domain.model.account.ListName
import com.elhady.movies.core.domain.model.common.Status
import com.elhady.movies.core.domain.usecase.account.AddToFavouriteUseCase
import com.elhady.movies.core.domain.usecase.account.AddToWatchList
import com.elhady.movies.core.domain.usecase.account.DeleteMovieFromDetailsListUseCase
import com.elhady.movies.core.domain.usecase.account.GetMyFavoriteListUseCase
import com.elhady.movies.core.domain.usecase.account.GetMyListDetailsByListIdUseCase
import com.elhady.movies.core.domain.usecase.account.GetMyWatchlistListUseCase
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.base.toErrorUiState
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.feature.watchlist.presentation.listcontents.mapper.ListContentsUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ListContentsViewModel @Inject constructor(
    private val stringsRes: StringsRes,
    private val getFavoriteUseCase: GetMyFavoriteListUseCase,
    private val getWatchlistUseCase: GetMyWatchlistListUseCase,
    private val getMovieListDetailsUseCase: GetMyListDetailsByListIdUseCase,
    private val deleteFavoriteUseCase: AddToFavouriteUseCase,
    private val deleteMovieFromDetailsListUseCase: DeleteMovieFromDetailsListUseCase,
    private val deleteWatchlistUseCase: AddToWatchList,
    private val listContentsUiMapper: ListContentsUiMapper,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ListContentsUiState, ListContentsUiEffect>(
    ListContentsUiState()
) {


    private val listType =
        savedStateHandle.get<String>("listType") ?: ""

    private val listName =
        savedStateHandle.get<String>("listName") ?: ""

    private val listId =
        savedStateHandle.get<Int>("listId") ?: 0




    init {
        updateTitle()
        getData()
    }

    private fun updateTitle() {
        _state.update {
            it.copy(
                title = when (listName) {
                    ListName.WATCHLIST.name -> stringsRes.watchlist
                    ListName.FAVORITE.name -> stringsRes.favourite
                    else -> listName
                }
            )
        }
    }

    fun onEvent(event: ListContentsUiEvent) {
        when (event) {

            is ListContentsUiEvent.MovieClicked -> {
                sendEffect(
                    ListContentsUiEffect.NavigateToMovieContents(
                        movieId = event.movieId
                    )
                )
            }

            is ListContentsUiEvent.TvShowClicked -> {
                sendEffect(
                    ListContentsUiEffect.NavigateToTvShowContents(
                        tvShowId = event.tvShowId
                    )
                )
            }

            is ListContentsUiEvent.DeleteMovieClicked -> {
                deleteMedia(event.position)
            }

            ListContentsUiEvent.BackClicked -> {
                sendEffect(
                    ListContentsUiEffect.NavigateBack
                )
            }

            ListContentsUiEvent.RetryClicked -> {
                getData()
            }
        }
    }

    private fun getData() {
        when (listName) {

            ListName.FAVORITE.name -> {
                getFavorite()
            }

            ListName.WATCHLIST.name -> {
                getWatchlist()
            }

            else -> {
                getMovieListDetails()
            }
        }
    }

    private fun getFavorite() {
        tryToExecute(
            call = {
                getFavoriteUseCase()
                    .map(listContentsUiMapper::map)
            },
            onSuccess = ::onGetMoviesSuccess,
            onError = ::onError,
        )
    }

    private fun getWatchlist() {
        tryToExecute(
            call = {
                getWatchlistUseCase()
                    .map(listContentsUiMapper::map)
            },
            onSuccess = ::onGetMoviesSuccess,
            onError = ::onError,
        )
    }

    private fun getMovieListDetails() {
        tryToExecute(
            call = {
                getMovieListDetailsUseCase(listId)
                    .map(listContentsUiMapper::map)
            },
            onSuccess = ::onGetMoviesSuccess,
            onError = ::onError,
        )
    }

    private fun onGetMoviesSuccess(
        movies: List<MovieUiState>
    ) {
        _state.update {
            it.copy(
                movies = movies,
                isLoading = false,
                error = null
            )
        }
    }

    private fun deleteMedia(position: Int) {
        val movie = state.value.movies.getOrNull(position) ?: return

        _state.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }
        when (listName) {
            ListName.FAVORITE.name -> {
                deleteFavorite(
                    mediaId = movie.id,
                    mediaType = movie.mediaType
                )
            }

            ListName.WATCHLIST.name -> {
                deleteWatchlist(
                    mediaId = movie.id,
                    mediaType = movie.mediaType
                )
            }

            else -> {
                deleteFromCustomList(
                    mediaId = movie.id
                )
            }
        }
    }

    private fun deleteFavorite(
        mediaId: Int,
        mediaType: String,
    ) {
        tryToExecute(
            call = {
                deleteFavoriteUseCase(
                    mediaId,
                    mediaType,
                    false
                )
            },
            onSuccess = ::onDeleteSuccess,
            onError = ::onError,
        )
    }

    private fun deleteWatchlist(
        mediaId: Int,
        mediaType: String,
    ) {
        tryToExecute(
            call = {
                deleteWatchlistUseCase(
                    mediaId,
                    mediaType,
                    false
                )
            },
            onSuccess = ::onDeleteSuccess,
            onError = ::onError,
        )
    }

    private fun deleteFromCustomList(
        mediaId: Int,
    ) {
        tryToExecute(
            call = {
                deleteMovieFromDetailsListUseCase(
                    listId = listId,
                    mediaId = mediaId
                )
            },
            onSuccess = ::onDeleteSuccess,
            onError = ::onError,
        )
    }

    private fun onDeleteSuccess(
        status: Status
    ) {
        getData()
    }

    private fun onError(
        throwable: AppException
    ) {
        _state.update {
            it.copy(
                isLoading = false,
                error = throwable.toErrorUiState()
            )
        }
        ListContentsUiEffect.ShowSnackBar(
            throwable.message
                ?: "No Network Connection"
        )
    }
}
