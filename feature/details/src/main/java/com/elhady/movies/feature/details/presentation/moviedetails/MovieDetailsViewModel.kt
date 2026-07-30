package com.elhady.movies.feature.details.presentation.moviedetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.base.StringsRes
import com.elhady.movies.core.domain.model.common.Status
import com.elhady.movies.core.domain.model.movie.MovieDetails
import com.elhady.movies.core.common.ForbiddenThrowable
import com.elhady.movies.core.domain.usecase.account.AddToUserListUseCase
import com.elhady.movies.core.domain.usecase.account.CreateUserListUseCase
import com.elhady.movies.core.domain.usecase.account.GetUserListsUseCase
import com.elhady.movies.core.domain.usecase.account.AddToFavouriteUseCase
import com.elhady.movies.core.domain.usecase.account.AddToWatchList
import com.elhady.movies.core.domain.usecase.movie.GetMovieDetailsUseCase
import com.elhady.movies.core.domain.usecase.movie.SetRatingUseCase
import com.elhady.movies.core.common.NoNetworkThrowable
import com.elhady.movies.core.common.UnauthorizedThrowable
import com.elhady.movies.core.domain.usecase.auth.CheckIsUserLoggedInUseCase
import com.elhady.movies.core.domain.usecase.movie.InsertMovieToWatchHistoryUseCase
import com.elhady.movies.core.domain.usecase.movie.GetRatingMovieUseCase
import com.elhady.movies.core.ui.listener.ChipListener
import com.elhady.movies.core.ui.listener.MediaListener
import com.elhady.movies.core.ui.listener.PeopleListener
import com.elhady.movies.core.ui.state.UserListUi
import com.elhady.movies.feature.details.presentation.moviedetails.mapper.CastUiMapper
import com.elhady.movies.feature.details.presentation.moviedetails.mapper.RecommendedUiStateMapper
import com.elhady.movies.feature.details.presentation.moviedetails.mapper.ReviewDetailsUiStateMapper
import com.elhady.movies.feature.details.presentation.moviedetails.mapper.ReviewsUiStateMapper
import com.elhady.movies.feature.details.presentation.moviedetails.mapper.UpperUiStateMapper
import com.elhady.movies.feature.details.presentation.moviedetails.mapper.UserListUiMapper
import com.elhady.movies.feature.details.presentation.moviedetails.mapper.WatchHistoryUiStateMapper
import com.elhady.movies.feature.details.presentation.moviedetails.rate.BottomSheetListener
import com.elhady.movies.feature.details.presentation.moviedetails.save.SaveToListListener
import com.elhady.movies.feature.details.presentation.tvdetails.mappers.TvRatingUiMapper
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
    private val recommendedUiStateMapper: RecommendedUiStateMapper,
    private val upperUiStateMapper: UpperUiStateMapper,
    private val reviewsUiStateMapper: ReviewsUiStateMapper,
    private val castUiMapper: CastUiMapper,
    private val reviewDetailsUiStateMapper: ReviewDetailsUiStateMapper,
    private val watchHistoryUiStateMapper: WatchHistoryUiStateMapper,
    private val userListsUiMapper: UserListUiMapper,
    private val getRatingMovieUseCase: GetRatingMovieUseCase,
    private val stringsRes: StringsRes,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MovieDetailsUiState, MovieDetailsUiEvent>(MovieDetailsUiState()),
    MovieDetailsListener, MediaListener, PeopleListener, ChipListener, BottomSheetListener,
    SaveToListListener {

    private val movieId = savedStateHandle.get<Int>("movieId")

    init {
        _state.update { it.copy(isLoading = true, isLogin = checkIsUserLoggedInUseCase()) }
        if (movieId != null) {
            getMovieDetails(movieId)
            getRatingMovie()

        } else {
            val errors = _state.value.onErrors.toMutableList()
            errors.add("There are a problem with MovieId")
            _state.update { it.copy(onErrors = errors, isLoading = false) }
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
//                id = movieDetails.id,
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
                onError(th)
            }
        }
    }

     fun onRatingSubmit() {
        tryToExecute(
            call = { ratingUseCase(movieId!!, state.value.userRating) },
            onSuccess = ::onRatingSuccess,
            onError = {
                sendEvent(MovieDetailsUiEvent.ApplyRatingEvent(stringsRes.someThingErrorWhenAddRating))
            }
        )
    }

    private fun onRatingSuccess(statusEntity: Status) {
        sendEvent(MovieDetailsUiEvent.ApplyRatingEvent(stringsRes.ratingAddSuccessFully))
        val item = TvRatingUiMapper().map(statusEntity)

        Log.d("Rate Success", "${state.value.userRating} ${item.ratingSuccess}")
    }


    fun updateRatingUiState(rate: Float) {
        _state.update { it.copy(userRating = rate) }
    }


    private fun getRatingMovie() {
        tryToExecute(
            call = { getRatingMovieUseCase(movieId!!) },
            onSuccess = ::onSuccessGetRating,
            onError = ::onError
        )
        Log.d("GetRating", "${state.value.userRating}  Movie Id $movieId")
    }

    private fun onSuccessGetRating(rate: Float) {
        _state.update { it.copy(userRating = rate) }
    }

    //region user lists
    fun emptyUserLists() {
        _state.update {
            it.copy(userLists = emptyList())
        }
    }

    fun getUserLists() {
        tryToExecute(
            call = { getUserListsUseCase() },
            mapper = userListsUiMapper,
            onSuccess = ::onSuccessUserLists,
            onError = ::onError
        )
    }

    private fun onSuccessUserLists(userListsEntity: List<UserListUi>) {
        _state.update { it.copy(userLists = userListsEntity) }
    }


    fun onDone(listsId: List<Int>) {
        listsId.forEach { id ->
            tryToExecute(
                call = { addToUserListUseCase(id, movieId!!) },
                onSuccess = { showMessageWithSnackBar(messages = stringsRes.newListAddSuccessFully) },
                onError = ::onError
            )
        }

        onClickDone()
    }


    fun createUserNewList(listName: String) {
        tryToExecute(
            call = { createUserListUseCase(listName) },
            onSuccess = ::onSuccessCreateUserNewList,
            onError = ::onError
        )
    }

    private fun onSuccessCreateUserNewList(statusEntity: Status) {
        showMessageWithSnackBar(messages = "${statusEntity.success} :" + stringsRes.newListAddSuccessFully)
        getUserLists()
    }

    fun addToFavourite() {
        tryToExecute(
            call = { addToFavouriteUseCase(movieId = movieId!!, mediaType = "movie") },
            onSuccess = { showMessageWithSnackBar(messages = stringsRes.addSuccessfully) },
            onError = ::onError
        )
        Log.d("FAV: ", "$movieId")
    }

    fun addToWatchlist() {
        tryToExecute(
            call = { addToWatchList(movieId = movieId!!, mediaType = "movie") },
            onSuccess = { showMessageWithSnackBar(messages = stringsRes.addSuccessfully) },
            onError = ::onError
        )
        Log.d("WAT: ", "$movieId")
    }

//endregion


    private fun showMessageWithSnackBar(messages: String) {
        sendEvent(MovieDetailsUiEvent.ShowSnackBarMessageEvent(messages))
    }

    private fun onError(throwable: Throwable) {
        val errorMessage = throwable.message ?: stringsRes.someThingError
        when (throwable) {
            is NoNetworkThrowable -> showMessageWithSnackBar(stringsRes.noNetworkConnection)
            is UnauthorizedThrowable -> showMessageWithSnackBar(stringsRes.theRequestFailed)
            is ForbiddenThrowable -> showMessageWithSnackBar(stringsRes.duplicateEntity)
            else -> throwable.message.toString()
        }
        _state.update { it.copy(onErrors = listOf(errorMessage), isLoading = false) }
    }

    override fun onClickPeople(id: Int) {
        sendEvent(MovieDetailsUiEvent.NavigateToPeopleDetailsEvent(id))
    }


    override fun onClickPlayTrailer() {
        sendEvent(MovieDetailsUiEvent.PlayVideoTrailerEvent(state.value.movieUiState.videoKey))
    }


    override fun onClickRateMovie() {
        if (state.value.isLogin) {
            sendEvent(MovieDetailsUiEvent.RateMovieEvent)
        } else {
            showMessageWithSnackBar(stringsRes.notLoggedInToRate)
        }
    }


    // region Movie details
    override fun onClickBackButton() {
        sendEvent(MovieDetailsUiEvent.OnClickBackEvent)
    }

    fun onClickSaveButton() {
        sendEvent(MovieDetailsUiEvent.SaveToListEvent)
    }

    override fun onClickShowMore(movieId: Int) {
        sendEvent(MovieDetailsUiEvent.NavigateToShowMoreEvent(movieId))
    }

    override fun onClickMedia(id: Int) {
        sendEvent(MovieDetailsUiEvent.NavigateToMovieDetailsEvent(id))
    }

    fun tryAgain(movieId: Int) {
        _state.update { it.copy(isLoading = true, onErrors = emptyList()) }
        getMovieDetails(movieId)
    }

    override fun onChipClick(id: Int) {
        _state.update {
            it.copy(
                userSelectedLists = (it.userSelectedLists + id).distinct()
            )
        }
    }

// endregion

    // region Rating Bottom Sheet
    override fun onClickApplyRateBottomSheet() {
//        onRatingSubmit()
    }

// endregion


    // region Rating Bottom Sheet Clicks
    override fun onClickCreateList() {
        sendEvent(MovieDetailsUiEvent.CreateListEvent)
    }
// endregion

    override fun onClickDone() {
        sendEvent(MovieDetailsUiEvent.DoneEvent)
    }

    override fun onClickAddList() {
        sendEvent(MovieDetailsUiEvent.AddListEvent)
    }
//    override fun onClickFavourite() {
//        addToFavourite()
//    }
//
//    override fun onClickWatchlist() {
//        addToWatchlist()
//    }

    override fun onDismiss() {
        sendEvent(MovieDetailsUiEvent.CloseEvent)
    }

// endregion
}
