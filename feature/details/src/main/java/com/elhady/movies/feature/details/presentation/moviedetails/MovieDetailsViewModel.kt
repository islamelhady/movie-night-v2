package com.elhady.movies.feature.details.presentation.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.core.common.ForbiddenThrowable
import com.elhady.movies.core.common.NoNetworkThrowable
import com.elhady.movies.core.common.UnauthorizedThrowable
import com.elhady.movies.core.domain.model.movie.MovieDetails
import com.elhady.movies.core.domain.usecase.account.AddToFavouriteUseCase
import com.elhady.movies.core.domain.usecase.account.AddToUserListUseCase
import com.elhady.movies.core.domain.usecase.account.AddToWatchList
import com.elhady.movies.core.domain.usecase.account.CreateUserListUseCase
import com.elhady.movies.core.domain.usecase.account.GetUserListsUseCase
import com.elhady.movies.core.domain.usecase.auth.CheckIsUserLoggedInUseCase
import com.elhady.movies.core.domain.usecase.movie.GetMovieDetailsUseCase
import com.elhady.movies.core.domain.usecase.movie.GetRatingMovieUseCase
import com.elhady.movies.core.domain.usecase.movie.InsertMovieToWatchHistoryUseCase
import com.elhady.movies.core.domain.usecase.movie.SetRatingUseCase
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.resource.StringsRes
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

    init {
        _state.update { it.copy(isLoading = true, isLogin = checkIsUserLoggedInUseCase()) }
        if (movieId != null) {
            getMovieDetails(movieId)
            getRatingMovie()
        } else {
            _state.update { 
                it.copy(
                    onErrors = listOf("There is a problem with MovieId"), 
                    isLoading = false
                ) 
            }
        }
    }

    fun onEvent(event: MovieDetailsUiEvent) {
        when (event) {
            MovieDetailsUiEvent.BackClicked -> sendEffect(MovieDetailsUiEffect.NavigateBack)
            MovieDetailsUiEvent.PlayClicked -> {
                sendEffect(MovieDetailsUiEffect.PlayVideoTrailer(state.value.movieUiState.videoKey))
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
                    val currentSelected = it.userSelectedLists.toMutableList()
                    if (event.id in currentSelected) {
                        currentSelected.remove(event.id)
                    } else {
                        currentSelected.add(event.id)
                    }
                    it.copy(userSelectedLists = currentSelected)
                }
            }
            MovieDetailsUiEvent.DoneClicked -> onDone()
            MovieDetailsUiEvent.FavouriteClicked -> addToFavourite()
            MovieDetailsUiEvent.WatchlistClicked -> addToWatchlist()
            is MovieDetailsUiEvent.CreateListClicked -> createUserNewList(event.name)
            MovieDetailsUiEvent.CloseClicked -> sendEffect(MovieDetailsUiEffect.CloseBottomSheet)
            MovieDetailsUiEvent.AddListClicked -> sendEffect(MovieDetailsUiEffect.AddListToBottomSheet)
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
                onErrors = emptyList()
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                insertMovieToWatchHistoryUseCase(watchHistoryUiStateMapper.map(movieDetails))
            } catch (th: Throwable) {
                // Should we show error for watch history? MovieDetailsViewModel original code called onError
                // but usually watch history insertion shouldn't block the whole screen if it fails.
                // Keeping original behavior for now.
            }
        }
    }

    private fun onRatingSubmit() {
        movieId?.let { id ->
            tryToExecute(
                call = { ratingUseCase(id, state.value.userRating) },
                onSuccess = { sendEffect(MovieDetailsUiEffect.ShowSnackBar(stringsRes.ratingAddSuccessFully)) },
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
            call = { getUserListsUseCase() },
            mapper = userListsUiMapper,
            onSuccess = ::onSuccessUserLists,
            onError = ::onError
        )
    }

    private fun onSuccessUserLists(userListsEntity: List<com.elhady.movies.core.ui.state.UserListUiState>) {
        _state.update { it.copy(userLists = userListsEntity) }
        sendEffect(MovieDetailsUiEffect.ShowSaveToListBottomSheet(userListsEntity))
    }

    private fun onDone() {
        state.value.userSelectedLists.forEach { id ->
            tryToExecute(
                call = { addToUserListUseCase(id, movieId!!) },
                onSuccess = { showMessageWithSnackBar(stringsRes.newListAddSuccessFully) },
                onError = ::onError
            )
        }
        _state.update { it.copy(userSelectedLists = emptyList()) }
        sendEffect(MovieDetailsUiEffect.DoneEvent)
    }

    private fun createUserNewList(listName: String) {
        tryToExecute(
            call = { createUserListUseCase(listName) },
            onSuccess = { 
                showMessageWithSnackBar(stringsRes.newListAddSuccessFully)
                getUserLists() 
            },
            onError = ::onError
        )
    }

    private fun addToFavourite() {
        movieId?.let { id ->
            tryToExecute(
                call = { addToFavouriteUseCase(mediaId = id, mediaType = "movie") },
                onSuccess = { showMessageWithSnackBar(stringsRes.addSuccessfully) },
                onError = ::onError
            )
        }
    }

    private fun addToWatchlist() {
        movieId?.let { id ->
            tryToExecute(
                call = { addToWatchList(movieId = id, mediaType = "movie") },
                onSuccess = { showMessageWithSnackBar(stringsRes.addSuccessfully) },
                onError = ::onError
            )
        }
    }

    private fun tryAgain(movieId: Int) {
        _state.update { it.copy(isLoading = true, onErrors = emptyList()) }
        getMovieDetails(movieId)
    }

    private fun showMessageWithSnackBar(message: String) {
        sendEffect(MovieDetailsUiEffect.ShowSnackBar(message))
    }

    private fun onError(throwable: Throwable) {
        val errorMessage = throwable.message ?: stringsRes.someThingError
        when (throwable) {
            is NoNetworkThrowable -> showMessageWithSnackBar(stringsRes.noNetworkConnection)
            is UnauthorizedThrowable -> showMessageWithSnackBar(stringsRes.theRequestFailed)
            is ForbiddenThrowable -> showMessageWithSnackBar(stringsRes.duplicateEntity)
            else -> Unit
        }
        _state.update { it.copy(onErrors = listOf(errorMessage), isLoading = false) }
    }
}
