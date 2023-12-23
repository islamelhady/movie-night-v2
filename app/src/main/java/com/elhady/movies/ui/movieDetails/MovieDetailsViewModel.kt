package com.elhady.movies.ui.movieDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.models.MediaDetailsReview
import com.elhady.movies.domain.models.MovieDetails
import com.elhady.movies.domain.models.Rated
import com.elhady.movies.domain.models.RatingStatus
import com.elhady.movies.domain.usecases.GetSessionIdUseCase
import com.elhady.movies.domain.usecases.movieDetails.GetMovieDetailsUseCase
import com.elhady.movies.domain.usecases.movieDetails.GetMovieRateUseCase
import com.elhady.movies.domain.usecases.movieDetails.InsertWatchMoviesUseCase
import com.elhady.movies.domain.usecases.movieDetails.SetRatingUseCase
import com.elhady.movies.ui.adapter.MediaInteractionListener
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.home.adapters.ActorInteractionListener
import com.elhady.movies.ui.mappers.ActorUiMapper
import com.elhady.movies.ui.mappers.MediaUiMapper
import com.elhady.movies.ui.mappers.MovieDetailsUiMapper
import com.elhady.movies.ui.mappers.ReviewUiMapper
import com.elhady.movies.ui.models.ActorUiState
import com.elhady.movies.ui.models.MediaUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val movieDetailsUiMapper: MovieDetailsUiMapper,
    private val insertMoviesUseCase: InsertWatchMoviesUseCase,
    private val getMovieRateUseCase: GetMovieRateUseCase,
    private val setRatingUseCase: SetRatingUseCase,
    private val getSessionIdUseCase: GetSessionIdUseCase,
    private val actorUiMapper: ActorUiMapper,
    private val mediaUiMapper: MediaUiMapper,
    private val reviewUiMapper: ReviewUiMapper
) : BaseViewModel<DetailsUiState, MovieDetailsUiEvent>(DetailsUiState()),
    DetailsInteractionListener, ActorInteractionListener, MediaInteractionListener {


    val args = MovieDetailsFragmentArgs.fromSavedStateHandle(state)


    init {
        getData()
    }

    override fun getData() {
        _state.update { it.copy(isLoading = true, onErrors = emptyList()) }
        getMovieDetails(args.movieID)
        getMovieCast(args.movieID)
        getSimilarMovies(args.movieID)
        getMovieReviews(args.movieID)
        getLoginStatus()
    }

    private suspend fun addToWatchHistory(movie: MovieDetails) {
        insertMoviesUseCase(movie)
    }

    private fun getMovieDetails(movieId: Int) {
        tryToExecute(
            call = { getMovieDetailsUseCase.getMovieDetails(movieId) },
            onSuccess = ::onSuccessMovieDetails,
            onError = ::onError
        )
        viewModelScope.launch {
            addToWatchHistory(getMovieDetailsUseCase.getMovieDetails(movieId))
        }
    }

    private fun onSuccessMovieDetails(details: MovieDetails) {
        _state.update {
            it.copy(
                movieDetailsResult = movieDetailsUiMapper.map(details),
                isLoading = false,
                onErrors = emptyList()
            )
        }
    }

    private fun getMovieCast(movieId: Int) {
        tryToExecute(
            call = { getMovieDetailsUseCase.getMovieCast(movieId) },
            mapper = actorUiMapper,
            onSuccess = ::onSuccessMovieCast,
            onError = ::onError
        )
    }

    private fun onSuccessMovieCast(actor: List<ActorUiState>) {
        _state.update {
            it.copy(
                movieCastResult = actor,
                isLoading = false,
                onErrors = emptyList()
            )
        }
    }

    private fun getSimilarMovies(movieId: Int) {
        tryToExecute(
            call = { getMovieDetailsUseCase.getSimilarMovies(movieId) },
            mapper = mediaUiMapper,
            onSuccess = ::onSuccessSimilarMovies,
            onError = ::onError
        )
    }

    private fun onSuccessSimilarMovies(similar: List<MediaUiState>) {
        _state.update {
            it.copy(
                similarMoviesResult = similar,
                isLoading = false,
                onErrors = emptyList()
            )
        }
    }

    private fun getMovieReviews(movieID: Int) {
        tryToExecute(
            call = { getMovieDetailsUseCase.getMovieReview(movieID) },
            onSuccess = ::onSuccessMoviesReviews,
            onError = ::onError
        )
    }

    private fun onSuccessMoviesReviews(review: MediaDetailsReview) {
        val result = review.reviews.map(reviewUiMapper::map)
        _state.update {
            it.copy(
                movieReviewsResult = result,
                isLoading = false,
                onErrors = emptyList()
            )
        }
    }

    private fun getLoginStatus() {
        if (!getSessionIdUseCase().isNullOrEmpty()) {
            _state.update {
                it.copy(isLogin = true)
            }
            getRatedMovie(args.movieID)
        }
    }

    private fun getRatedMovie(movieId: Int) {
        tryToExecute(
            call = { getMovieRateUseCase(movieId) },
            onSuccess = ::onSuccessRatedMovie,
            onError = ::onError
        )
    }

    private fun onSuccessRatedMovie(value: Float) {
        _state.update { it.copy(ratingValue = value, isLoading = false, onErrors = emptyList()) }

    }

    fun onChangeRating(value: Float) {
        tryToExecute(
            call = { setRatingUseCase(movieId = args.movieID, value = value) },
            onSuccess = ::onSuccessChangeRating,
            onError = ::onError
        )
    }

    private fun onSuccessChangeRating(rated: RatingStatus) {
        sendEvent(MovieDetailsUiEvent.MessageAppear(rated.statusMessage))
    }

    private fun onError(error: Throwable) {
        val errors = _state.value.onErrors.toMutableList()
        errors.add(error.message.toString())
        _state.update { it.copy(onErrors = errors, isLoading = false) }
    }

    override fun onClickBackButton() {
        sendEvent(MovieDetailsUiEvent.ClickBackButton)
    }

    override fun onClickFavourite() {
        sendEvent(MovieDetailsUiEvent.ClickFavourite)
    }

    override fun onClickActor(actorID: Int) {
        sendEvent(MovieDetailsUiEvent.ClickCastEvent(castId = actorID))
    }

    override fun onClickPlayTrailer() {
        sendEvent(MovieDetailsUiEvent.ClickPlayTrailerEvent)
    }

    override fun onclickViewReviews() {
        sendEvent(MovieDetailsUiEvent.ClickSeeReviewsEvent)
    }

    override fun onClickMedia(movieId: Int) {
        sendEvent(MovieDetailsUiEvent.ClickMovieEvent(movieId))
    }


}