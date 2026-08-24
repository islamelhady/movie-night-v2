package com.elhady.movies.feature.watchlist.presentation.watchhistory

import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.domain.usecase.movie.DeleteMovieFromWatchHistoryUseCase
import com.elhady.movies.core.domain.usecase.movie.GetAllWatchHistoryMoviesUseCase
import com.elhady.movies.core.domain.usecase.movie.SearchWatchHistoryUseCase
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.base.toErrorUiState
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.feature.watchlist.presentation.watchhistory.mapper.MovieDomainMapper
import com.elhady.movies.feature.watchlist.presentation.watchhistory.mapper.MovieUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WatchHistoryViewModel @Inject constructor(
    private val getAllWatchHistoryMoviesUseCase: GetAllWatchHistoryMoviesUseCase,
    private val deleteMovieFromWatchHistoryUseCase: DeleteMovieFromWatchHistoryUseCase,
    private val searchWatchHistoryUseCase: SearchWatchHistoryUseCase,
    private val movieDomainMapper: MovieDomainMapper,
    private val movieUiStateMapper: MovieUiMapper,
    private val stringsRes: StringsRes
) : BaseViewModel<WatchHistoryUiState, WatchHistoryUiEffect>(
    WatchHistoryUiState()
) {

    private val itemsCreator = WatchHistoryRecyclerItemsCreator(stringsRes)

    init {
        getAllMovies()
    }

    fun onEvent(event: WatchHistoryUiEvent) {
        when (event) {

            is WatchHistoryUiEvent.SearchQueryChanged -> {
                onSearchQueryChanged(event.query)
            }

            is WatchHistoryUiEvent.MovieClicked -> {
                sendEffect(
                    WatchHistoryUiEffect.NavigateToMovieDetails(
                        event.movieId
                    )
                )
            }

            is WatchHistoryUiEvent.MovieSwiped -> {
                deleteMovie(event.position)
            }

            WatchHistoryUiEvent.UndoDeleteClicked -> {
                undoDelete()
            }

            WatchHistoryUiEvent.DeleteSnackBarDismissed -> {
                confirmDelete()
            }

            WatchHistoryUiEvent.RetryClicked -> {
                getAllMovies()
            }

            WatchHistoryUiEvent.BackClicked -> {
                sendEffect(WatchHistoryUiEffect.NavigateBack)
            }

        }
    }

    private fun getAllMovies() {
        _state.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }

        tryToExecute(
            call = {
                getAllWatchHistoryMoviesUseCase()
                    .map(movieUiStateMapper::map)
                    .let(itemsCreator::createItems)
            },
            onSuccess = ::onMoviesLoaded,
            onError = ::onError
        )
    }

    private fun onMoviesLoaded(
        movies: List<WatchHistoryRecyclerItem>
    ) {
        _state.update {
            it.copy(
                movies = movies,
                isLoading = false,
                error = null
            )
        }
    }

    private fun onSearchQueryChanged(query: String) {
        _state.update {
            it.copy(searchInput = query)
        }

        searchMovies(query)
    }

    private fun searchMovies(query: String) {
        _state.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }

        tryToExecute(
            call = {
                val movies = if (query.isBlank()) {
                    getAllWatchHistoryMoviesUseCase()
                } else {
                    searchWatchHistoryUseCase(query)
                }
                movies.map(movieUiStateMapper::map)
                    .let(itemsCreator::createItems)
            },
            onSuccess = ::onMoviesLoaded,
            onError = ::onError
        )
    }

    private fun deleteMovie(position: Int) {
        val items = state.value.movies

        if (position !in items.indices) return

        val item = items[position]

        if (item !is WatchHistoryRecyclerItem.MovieCard) return

        val newItems = items.toMutableList()

        val deletedMovie = item.movie

        newItems.removeAt(position)

        val deletedTitle = if (shouldRemoveTitle(position, newItems)) {
            val titlePosition = position - 1

            if (titlePosition in newItems.indices &&
                newItems[titlePosition] is WatchHistoryRecyclerItem.Title
            ) {
                val title = newItems.removeAt(titlePosition)
                        as WatchHistoryRecyclerItem.Title

                title.title
            } else {
                null
            }
        } else {
            null
        }

        _state.update {
            it.copy(
                movies = newItems,
                pendingDeletion = PendingDeletion(
                    movie = deletedMovie,
                    title = deletedTitle,
                    position = position
                )
            )
        }

        sendEffect(WatchHistoryUiEffect.ShowDeleteSnackBar)
    }

    private fun shouldRemoveTitle(
        deletedPosition: Int,
        itemsAfterDeletion: List<WatchHistoryRecyclerItem>
    ): Boolean {

        val titlePosition = deletedPosition - 1

        if (
            titlePosition !in itemsAfterDeletion.indices ||
            itemsAfterDeletion[titlePosition] !is WatchHistoryRecyclerItem.Title
        ) {
            return false
        }

        return (
                titlePosition + 1 !in itemsAfterDeletion.indices ||
                        itemsAfterDeletion[titlePosition + 1] is WatchHistoryRecyclerItem.Title
                )
    }

    private fun undoDelete() {
        val pendingDeletion = state.value.pendingDeletion
            ?: return

        val items = state.value.movies.toMutableList()

        pendingDeletion.title?.let { title ->
            items.add(
                pendingDeletion.position - 1,
                WatchHistoryRecyclerItem.Title(title)
            )
        }

        items.add(
            pendingDeletion.position,
            WatchHistoryRecyclerItem.MovieCard(
                pendingDeletion.movie
            )
        )

        _state.update {
            it.copy(
                movies = items,
                pendingDeletion = null
            )
        }
    }

    private fun confirmDelete() {
        val pendingDeletion = state.value.pendingDeletion
            ?: return

        tryToExecute(
            call = {
                deleteMovieFromWatchHistoryUseCase(
                    movieDomainMapper.map(pendingDeletion.movie)
                )
            },
            onSuccess = {
                _state.update {
                    it.copy(
                        pendingDeletion = null
                    )
                }
            },
            onError = ::onError
        )
    }

    private fun onError(exception: AppException) {
        val error = exception.toErrorUiState()

        _state.update {
            it.copy(
                isLoading = false,
                error = error
            )
        }

        sendEffect(
            WatchHistoryUiEffect.ShowErrorSnackBar(error)
        )
    }
}
