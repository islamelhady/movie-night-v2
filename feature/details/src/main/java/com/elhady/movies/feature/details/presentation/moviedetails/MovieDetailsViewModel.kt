package com.elhady.movies.feature.details.presentation.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.common.MediaType
import com.elhady.movies.core.domain.model.account.CreateList
import com.elhady.movies.core.domain.model.movie.MovieDetails
import com.elhady.movies.core.domain.usecase.account.AddToFavouriteUseCase
import com.elhady.movies.core.domain.usecase.account.AddToUserListUseCase
import com.elhady.movies.core.domain.usecase.account.AddToWatchList
import com.elhady.movies.core.domain.usecase.account.CreateUserListUseCase
import com.elhady.movies.core.domain.usecase.account.DeleteMovieFromDetailsListUseCase
import com.elhady.movies.core.domain.usecase.account.GetUserListsUseCase
import com.elhady.movies.core.domain.usecase.auth.CheckIsUserLoggedInUseCase
import com.elhady.movies.core.domain.usecase.movie.GetMovieDetailsUseCase
import com.elhady.movies.core.domain.usecase.movie.GetRatingMovieUseCase
import com.elhady.movies.core.domain.usecase.movie.InsertMovieToWatchHistoryUseCase
import com.elhady.movies.core.domain.usecase.movie.SetRatingUseCase
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.base.ErrorUiState
import com.elhady.movies.core.ui.base.UiText
import com.elhady.movies.core.ui.base.toErrorUiState
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.core.ui.state.SaveToListsUiState
import com.elhady.movies.feature.details.presentation.moviedetails.mapper.CastUiMapper
import com.elhady.movies.feature.details.presentation.moviedetails.mapper.RecommendedUiMapper
import com.elhady.movies.feature.details.presentation.moviedetails.mapper.ReviewDetailsUiMapper
import com.elhady.movies.feature.details.presentation.moviedetails.mapper.ReviewsUiMapper
import com.elhady.movies.feature.details.presentation.moviedetails.mapper.UpperUiMapper
import com.elhady.movies.feature.details.presentation.moviedetails.mapper.UserListUiMapper
import com.elhady.movies.feature.details.presentation.moviedetails.mapper.WatchHistoryUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsUseCase: GetMovieDetailsUseCase,
    private val ratingUseCase: SetRatingUseCase,
    private val getUserListsUseCase: GetUserListsUseCase,
    private val addToUserListUseCase: AddToUserListUseCase,
    private val createUserListUseCase: CreateUserListUseCase,
    private val deleteMovieFromDetailsListUseCase: DeleteMovieFromDetailsListUseCase,
    private val addToFavouriteUseCase: AddToFavouriteUseCase,
    private val addToWatchList: AddToWatchList,
    private val insertMovieToWatchHistoryUseCase: InsertMovieToWatchHistoryUseCase,
    private val checkIsUserLoggedInUseCase: CheckIsUserLoggedInUseCase,
    private val recommendedUiStateMapper: RecommendedUiMapper,
    private val upperUiStateMapper: UpperUiMapper,
    private val reviewsUiStateMapper: ReviewsUiMapper,
    private val castUiMapper: CastUiMapper,
    private val reviewDetailsUiStateMapper: ReviewDetailsUiMapper,
    private val watchHistoryUiStateMapper: WatchHistoryUiMapper,
    private val userListsUiMapper: UserListUiMapper,
    private val getRatingMovieUseCase: GetRatingMovieUseCase,
    private val stringsRes: StringsRes,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MovieDetailsUiState, MovieDetailsUiEffect>(MovieDetailsUiState()) {

    private val movieId = savedStateHandle.get<Int>("movieId")
    private var initialSaveToListsState = SaveToListsUiState()

    init {
        if (movieId != null) {
            _state.update { it.copy(isLoading = true, isLogin = checkIsUserLoggedInUseCase()) }
            getMovieDetails(movieId)
            getRatingMovie()
        } else {
            _state.update { 
                it.copy(
                    error = ErrorUiState.Generic, 
                    isLoading = false
                ) 
            }
        }
    }

    fun onEvent(event: MovieDetailsUiEvent) {
        when (event) {
            MovieDetailsUiEvent.BackClicked -> sendEffect(MovieDetailsUiEffect.NavigateBack)
            MovieDetailsUiEvent.PlayClicked -> {
                _state.update { it.copy(isPlayerVisible = true) }
            }
            MovieDetailsUiEvent.DismissPlayerClicked -> {
                _state.update { it.copy(isPlayerVisible = false) }
            }
            MovieDetailsUiEvent.RateClicked -> {
                if (state.value.isLogin) {
                    sendEffect(MovieDetailsUiEffect.ShowRateBottomSheet)
                } else {
                    showMessageWithSnackBar(stringsRes.notLoggedInToRate)
                }
            }
            MovieDetailsUiEvent.SaveClicked -> {
                if (state.value.isLogin) {
                    getUserLists()
                } else {
                    showMessageWithSnackBar(stringsRes.notLoggedInToRate)
                }
            }
            is MovieDetailsUiEvent.ShowMoreClicked -> {
                sendEffect(MovieDetailsUiEffect.NavigateToShowMore(event.movieId))
            }
            is MovieDetailsUiEvent.MovieClicked -> {
                sendEffect(MovieDetailsUiEffect.NavigateToMovieDetails(event.movieId))
            }
            is MovieDetailsUiEvent.PersonClicked -> {
                sendEffect(MovieDetailsUiEffect.NavigateToPeopleDetails(event.personId))
            }
            is MovieDetailsUiEvent.RatingChanged -> {
                _state.update { it.copy(userRating = event.rating) }
            }
            MovieDetailsUiEvent.RatingSubmitted -> onRatingSubmit()
            is MovieDetailsUiEvent.RetryClicked -> tryAgain(event.movieId)
            is MovieDetailsUiEvent.ChipClicked -> {
                _state.update {
                    val currentSelected = it.saveToListsUiState.selectedUserLists.toMutableList()
                    if (event.id in currentSelected) {
                        currentSelected.remove(event.id)
                    } else {
                        currentSelected.add(event.id)
                    }
                    it.copy(
                        saveToListsUiState = it.saveToListsUiState.copy(
                            selectedUserLists = currentSelected,
                            isCreateListVisible = false
                        )
                    )
                }
            }
            MovieDetailsUiEvent.DoneClicked -> onDone()
            MovieDetailsUiEvent.FavouriteClicked -> {
                _state.update {
                    it.copy(
                        saveToListsUiState = it.saveToListsUiState.copy(
                            isFavouriteSelected = !it.saveToListsUiState.isFavouriteSelected,
                            isCreateListVisible = false
                        )
                    )
                }
            }
            MovieDetailsUiEvent.WatchlistClicked -> {
                _state.update {
                    it.copy(
                        saveToListsUiState = it.saveToListsUiState.copy(
                            isWatchlistSelected = !it.saveToListsUiState.isWatchlistSelected,
                            isCreateListVisible = false
                        )
                    )
                }
            }
            is MovieDetailsUiEvent.CreateListClicked -> createUserNewList(event.name)
            MovieDetailsUiEvent.CloseClicked -> {
                _state.update { it.copy(saveToListsUiState = it.saveToListsUiState.copy(isCreateListVisible = false)) }
                sendEffect(MovieDetailsUiEffect.CloseBottomSheet)
            }
            MovieDetailsUiEvent.AddListClicked -> {
                _state.update { it.copy(saveToListsUiState = it.saveToListsUiState.copy(isCreateListVisible = true)) }
            }
        }
    }

    private fun getMovieDetails(movieId: Int) {
        tryToExecute(
            call = { movieDetailsUseCase(movieId) },
            onSuccess = ::onSuccessMovieDetails,
            onError = ::onError
        )
    }

    private fun onSuccessMovieDetails(movieDetails: MovieDetails) {
        _state.update {
            it.copy(
                id = movieDetails.id,
                movieUiState = upperUiStateMapper.map(movieDetails),
                recommendedUiState = recommendedUiStateMapper.map(movieDetails.recommendations.recommendedMovies),
                reviewUiState = reviewsUiStateMapper.map(movieDetails.reviewEntity.reviews),
                castUiState = castUiMapper.map(movieDetails.credits.cast),
                reviewsDetails = reviewDetailsUiStateMapper.map(movieDetails),
                isLoading = false,
                error = null,
                saveToListsUiState = it.saveToListsUiState.copy(
                    isFavouriteSelected = movieDetails.accountStates?.favorite ?: false,
                    isWatchlistSelected = movieDetails.accountStates?.watchlist ?: false
                )
            )
        }
        initialSaveToListsState = initialSaveToListsState.copy(
            isFavouriteSelected = movieDetails.accountStates?.favorite ?: false,
            isWatchlistSelected = movieDetails.accountStates?.watchlist ?: false
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                insertMovieToWatchHistoryUseCase(watchHistoryUiStateMapper.map(movieDetails))
            } catch (th: Throwable) {
                // Silently fail or log for background tasks
            }
        }
    }

    private fun onRatingSubmit() {
        movieId?.let { id ->
            tryToExecute(
                call = { ratingUseCase(id, state.value.userRating) },
                onSuccess = { 
                    sendEffect(MovieDetailsUiEffect.ShowSnackBar(stringsRes.ratingAddSuccessFully))
                    getMovieDetails(id)
                },
                onError = { sendEffect(MovieDetailsUiEffect.ShowSnackBar(stringsRes.someThingErrorWhenAddRating)) }
            )
        }
    }

    private fun getRatingMovie() {
        movieId?.let { id ->
            tryToExecute(
                call = { getRatingMovieUseCase(id) },
                onSuccess = { rate -> _state.update { it.copy(userRating = rate) } },
                onError = ::onError
            )
        }
    }

    private fun getUserLists() {
        tryToExecute(
            call = { getUserListsUseCase(movieId, MediaType.MOVIE) },
            mapper = userListsUiMapper,
            onSuccess = ::onSuccessUserLists,
            onError = ::onError
        )
    }

    private fun onSuccessUserLists(userListsEntity: List<com.elhady.movies.core.ui.state.UserListUiState>) {
        val selectedIds = userListsEntity.filter { it.isSelected }.map { it.id }
        _state.update {
            it.copy(
                userLists = userListsEntity,
                saveToListsUiState = it.saveToListsUiState.copy(
                    selectedUserLists = selectedIds,
                    isCreateListVisible = false
                )
            )
        }
        initialSaveToListsState = initialSaveToListsState.copy(selectedUserLists = selectedIds)
        sendEffect(MovieDetailsUiEffect.ShowSaveToListBottomSheet(userListsEntity))
    }

    private fun onDone() {
        val currentId = movieId!!
        val currentState = state.value.saveToListsUiState

        viewModelScope.launch {
            try {
                // Execute required operations
                if (currentState.isFavouriteSelected != initialSaveToListsState.isFavouriteSelected) {
                    addToFavouriteUseCase(currentId, MediaType.MOVIE, currentState.isFavouriteSelected)
                }

                if (currentState.isWatchlistSelected != initialSaveToListsState.isWatchlistSelected) {
                    addToWatchList(currentId, MediaType.MOVIE, currentState.isWatchlistSelected)
                }

                val added = currentState.selectedUserLists.filter { it !in initialSaveToListsState.selectedUserLists }
                val removed = initialSaveToListsState.selectedUserLists.filter { it !in currentState.selectedUserLists }

                added.forEach { listId ->
                    addToUserListUseCase(listId, currentId, MediaType.MOVIE)
                }

                removed.forEach { listId ->
                    deleteMovieFromDetailsListUseCase(listId, currentId)
                }

                // If we reach here, all operations succeeded
                val isChanged = currentState.isFavouriteSelected != initialSaveToListsState.isFavouriteSelected ||
                        currentState.isWatchlistSelected != initialSaveToListsState.isWatchlistSelected ||
                        added.isNotEmpty() || removed.isNotEmpty()

                if (isChanged) {
                    showMessageWithSnackBar(stringsRes.addSuccessfully)
                }

                _state.update {
                    it.copy(
                        saveToListsUiState = it.saveToListsUiState.copy(selectedUserLists = emptyList())
                    )
                }
                sendEffect(MovieDetailsUiEffect.CloseBottomSheet)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    private fun createUserNewList(listName: String) {
        if (state.value.saveToListsUiState.isLoading) return
        if (listName.isBlank()) {
            showMessageWithSnackBar(stringsRes.emptyField)
            return
        }

        _state.update { it.copy(saveToListsUiState = it.saveToListsUiState.copy(isLoading = true)) }
        tryToExecute(
            call = { createUserListUseCase(listName) },
            onSuccess = ::onCreateListSuccess,
            onError = ::onCreateListError
        )
    }

    private fun onCreateListSuccess(createList: CreateList) {
        val listId = createList.listId
        if (createList.success == true && listId != null) {
            val currentId = movieId!!
            tryToExecute(
                call = { addToUserListUseCase(listId, currentId, MediaType.MOVIE) },
                onSuccess = {
                    _state.update { it.copy(saveToListsUiState = it.saveToListsUiState.copy(isLoading = false)) }
                    showMessageWithSnackBar(stringsRes.newListAddSuccessFully)
                    getUserLists()
                    sendEffect(MovieDetailsUiEffect.CloseBottomSheet)
                },
                onError = ::onCreateListError
            )
        } else {
            onCreateListError(AppException.Unknown(createList.statusMessage ?: ""))
        }
    }

    private fun onCreateListError(throwable: Throwable) {
        _state.update { it.copy(saveToListsUiState = it.saveToListsUiState.copy(isLoading = false)) }
        onError(throwable)
    }

    private fun tryAgain(movieId: Int) {
        _state.update { it.copy(isLoading = true, error = null) }
        getMovieDetails(movieId)
    }

    private fun showMessageWithSnackBar(message: UiText) {
        sendEffect(MovieDetailsUiEffect.ShowSnackBar(message))
    }

    private fun onError(throwable: Throwable) {
        val message = when (throwable) {
            is AppException.NoNetwork -> stringsRes.noNetworkConnection
            is AppException.Timeout -> stringsRes.timeOut
            else -> stringsRes.someThingError
        }

        if (state.value.movieUiState.title.isNotBlank()) {
            showMessageWithSnackBar(message)
            _state.update { it.copy(isLoading = false) }
        } else {
            val errorUiState = if (throwable is AppException) throwable.toErrorUiState() else ErrorUiState.Generic
            _state.update { it.copy(error = errorUiState, isLoading = false) }
        }
    }
}
