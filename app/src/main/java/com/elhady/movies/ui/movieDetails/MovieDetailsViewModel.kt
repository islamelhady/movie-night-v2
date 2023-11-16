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
import com.elhady.movies.ui.mappers.ReviewUiMapper
import com.elhady.movies.utilities.Constants
import com.elhady.movies.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
) : BaseViewModel(), DetailsInteractionListener, ActorInteractionListener, MediaInteractionListener{


    val args = MovieDetailsFragmentArgs.fromSavedStateHandle(state)

    private val _detailsUiState = MutableStateFlow(DetailsUiState())
    val detailsUiState = _detailsUiState.asStateFlow()

    private val _detailsUiEvent = MutableStateFlow<Event<MovieDetailsUiEvent?>>(Event(null))
    val detailsUiEvent = _detailsUiEvent.asStateFlow()


    init {
        getData()
    }

    override fun getData() {
        _detailsUiState.update { it.copy(isLoading = true, errorUIStates = emptyList()) }
        getMovieDetails(args.movieID)
        getMovieCast(args.movieID)
        getSimilarMovies(args.movieID)
        getMovieReviews(args.movieID)
        getLoginStatus()
    }

    private suspend fun addToWatchHistory(movie: MovieDetails){
        insertMoviesUseCase(movie)
    }
    private fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                val result = getMovieDetailsUseCase.getMovieDetails(movieId)
                _detailsUiState.update {
                    it.copy(movieDetailsResult =  movieDetailsUiMapper.map(result), isLoading = false)
                }
                onAddMovieDetailsItemOfNestedView(DetailsItem.Header(_detailsUiState.value.movieDetailsResult))
                addToWatchHistory(result)
            } catch (e: Exception) {
                _detailsUiState.update {
                    it.copy(
                        errorUIStates = listOf(
                            ErrorUiState(
                                code = Constants.INTERNET_STATUS,
                                message = e.message.toString()
                            )
                        ), isLoading = false
                    )
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
                _detailsUiState.update {
                    it.copy(movieCastResult = result, isLoading = false)
                }
                onAddMovieDetailsItemOfNestedView(DetailsItem.Cast(result))
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
                _detailsUiState.update {
                    it.copy(similarMoviesResult = result, isLoading = false)
                }
                onAddMovieDetailsItemOfNestedView(DetailsItem.Similar(_detailsUiState.value.similarMoviesResult))
            } catch (e: Exception) {

            }
        }
    }

    private fun getMovieReviews(movieID: Int) {
        viewModelScope.launch {
            try {
                val result = getMovieDetailsUseCase.getMovieReview(movieId = movieID)
                _detailsUiState.update {
                    it.copy(movieReviewsResult = result.reviews.map(reviewUiMapper::map))
                }
                if (result.reviews.isNotEmpty()) {
                    setReviews(result.isMoreThanMax)
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun setReviews(seeAllReviews: Boolean) {
        _detailsUiState.value.movieReviewsResult.forEach {
            onAddMovieDetailsItemOfNestedView(DetailsItem.Reviews(it))
        }
        onAddMovieDetailsItemOfNestedView(DetailsItem.ReviewsText)
        if (seeAllReviews) {
            onAddMovieDetailsItemOfNestedView(DetailsItem.SeeAllReviewsButton)
        }
    }

    private fun getLoginStatus(){
        if (!getSessionIdUseCase().isNullOrEmpty()){
            _detailsUiState.update {
                it.copy(isLogin = true)
            }
            getRatedMovie(args.movieID)
        }
    }
    private fun getRatedMovie(movieId: Int){
        viewModelScope.launch {
            val result = getMovieRateUseCase(movieId)
            _detailsUiState.update {
                it.copy(ratingValue = result)
            }
            onAddMovieDetailsItemOfNestedView(DetailsItem.Rating(this@MovieDetailsViewModel))
        }
    }

    fun onChangeRating(value: Float){
        viewModelScope.launch {
            setRatingUseCase(movieId = args.movieID, value = value)
            _detailsUiState.update { it.copy(ratingValue = value) }
            _detailsUiEvent.update { Event(MovieDetailsUiEvent.MessageAppear) }
        }
    }

    private fun onAddMovieDetailsItemOfNestedView(items: DetailsItem) {
        val listItems = _detailsUiState.value.detailsItemsResult.toMutableList()
        listItems.add(items)
        _detailsUiState.update { it.copy(detailsItemsResult = listItems.toList()) }
    }

    override fun onClickBackButton() {
        _detailsUiEvent.update {
            Event(MovieDetailsUiEvent.ClickBackButton)
        }
    }

    override fun onClickActor(actorID: Int) {
        _detailsUiEvent.update {
            Event(MovieDetailsUiEvent.ClickCastEvent(castId = actorID))
        }
    }

    override fun onClickPlayTrailer() {
        _detailsUiEvent.update {
            Event(MovieDetailsUiEvent.ClickPlayTrailerEvent)
        }
    }

    override fun onclickViewReviews() {
        _detailsUiEvent.update {
            Event(MovieDetailsUiEvent.ClickSeeReviewsEvent)
        }
    }

    override fun onClickMedia(movieId: Int) {
        _detailsUiEvent.update {
            Event(MovieDetailsUiEvent.ClickMovieEvent(movieId))
        }
    }


}