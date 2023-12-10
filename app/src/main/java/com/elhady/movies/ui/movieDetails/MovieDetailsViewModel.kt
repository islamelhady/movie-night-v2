package com.elhady.movies.ui.movieDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.models.MovieDetails
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
        viewModelScope.launch {
            try {
                val result = getMovieDetailsUseCase.getMovieDetails(movieId)
                _state.update {
                    it.copy(
                        movieDetailsResult = movieDetailsUiMapper.map(result),
                        isLoading = false
                    )
                }
                addToWatchHistory(result)
            } catch (e: Exception) {
                _state.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    private fun getMovieCast(movieId: Int) {
        viewModelScope.launch {
            try {
                val result = getMovieDetailsUseCase.getMovieCast(movieId = movieId).map {
                    actorUiMapper.map(it)
                }
                _state.update {
                    it.copy(movieCastResult = result, isLoading = false)
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun getSimilarMovies(movieId: Int) {
        viewModelScope.launch {
            try {
                val result = getMovieDetailsUseCase.getSimilarMovies(movieId = movieId).map {
                    mediaUiMapper.map(it)
                }
                _state.update {
                    it.copy(similarMoviesResult = result, isLoading = false)
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun getMovieReviews(movieID: Int) {
        viewModelScope.launch {
            try {
                val result = getMovieDetailsUseCase.getMovieReview(movieId = movieID)
                _state.update {
                    it.copy(movieReviewsResult = result.reviews.map(reviewUiMapper::map))
                }
            } catch (e: Exception) {

            }
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
        viewModelScope.launch {
            val result = getMovieRateUseCase(movieId)
            _state.update {
                it.copy(ratingValue = result)
            }
        }
    }

    fun onChangeRating(value: Float) {
        viewModelScope.launch {
            setRatingUseCase(movieId = args.movieID, value = value)
            _state.update { it.copy(ratingValue = value) }
            sendEvent(MovieDetailsUiEvent.MessageAppear)
        }
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