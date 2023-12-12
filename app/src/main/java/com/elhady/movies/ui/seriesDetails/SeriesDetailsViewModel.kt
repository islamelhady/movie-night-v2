package com.elhady.movies.ui.seriesDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.models.Media
import com.elhady.movies.domain.models.SeriesDetails
import com.elhady.movies.domain.usecases.GetSessionIdUseCase
import com.elhady.movies.domain.usecases.seriesDetails.GetSeriesDetailsUseCase
import com.elhady.movies.domain.usecases.seriesDetails.GetSeriesRateUseCase
import com.elhady.movies.domain.usecases.seriesDetails.InsertWatchSeriesUseCase
import com.elhady.movies.domain.usecases.seriesDetails.SetRatingUseCase
import com.elhady.movies.ui.adapter.MediaInteractionListener
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.home.adapters.ActorInteractionListener
import com.elhady.movies.ui.mappers.ActorUiMapper
import com.elhady.movies.ui.mappers.MediaUiMapper
import com.elhady.movies.ui.mappers.ReviewUiMapper
import com.elhady.movies.ui.models.ActorUiState
import com.elhady.movies.ui.models.MediaUiState
import com.elhady.movies.ui.movieDetails.DetailsInteractionListener
import com.elhady.movies.ui.seriesDetails.seriesUiMapper.SeasonUiMapper
import com.elhady.movies.ui.seriesDetails.seriesUiMapper.SeriesDetailsUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesDetailsViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getSeriesDetailsUseCase: GetSeriesDetailsUseCase,
    private val seriesDetailsUiMapper: SeriesDetailsUiMapper,
    private val insertWatchSeriesUseCase: InsertWatchSeriesUseCase,
    private val getSeriesRateUseCase: GetSeriesRateUseCase,
    private val getSessionIdUseCase: GetSessionIdUseCase,
    private val setRatingUseCase: SetRatingUseCase,
    private val actorUiMapper: ActorUiMapper,
    private val mediaUiMapper: MediaUiMapper,
    private val seasonUiMapper: SeasonUiMapper,
    private val reviewUiMapper: ReviewUiMapper
) : BaseViewModel<SeriesDetailsUiState, SeriesDetailsUiEvent>(SeriesDetailsUiState()),
    DetailsInteractionListener, ActorInteractionListener, MediaInteractionListener,
    SeasonInteractionListener {

    val args = SeriesDetailsFragmentArgs.fromSavedStateHandle(state)

    init {
        getData()
    }

    override fun getData() {
        getTVShowDetails(args.seriesId)
        getSeriesCast(args.seriesId)
        getSimilarSeries(args.seriesId)
        getSeasonSeries(args.seriesId)
//        getSeriesReview(args.seriesId)
        getLoginStatus()
    }

    private suspend fun addWatchHistory(series: SeriesDetails) {
        insertWatchSeriesUseCase(series)
    }

    private fun getTVShowDetails(tvShowId: Int) {
        tryToExecute(
            call = { getSeriesDetailsUseCase.getSeriesDetails(tvShowId) },
            onSuccess = ::onSuccessTvShowDetails,
            onError = ::onError
        )
        viewModelScope.launch {
            addWatchHistory(getSeriesDetailsUseCase.getSeriesDetails(tvShowId))
        }
    }

    private fun onSuccessTvShowDetails(details: SeriesDetails) {
        _state.update { it.copy(seriesDetailsResult = seriesDetailsUiMapper.map(details), isLoading = false, onError = emptyList()) }
    }

    private fun getSeriesCast(tvShowId: Int) {
        tryToExecute(
            call = { getSeriesDetailsUseCase.getSeriesCast(tvShowId) },
            mapper = actorUiMapper,
            onSuccess = ::onSuccessSeriesCast,
            onError = ::onError
        )
    }

    private fun onSuccessSeriesCast(cast: List<ActorUiState>) {
        _state.update { it.copy(seriesCastResult = cast, isLoading = false, onError = emptyList()) }
    }

    private fun getSimilarSeries(seriesId: Int) {
        tryToExecute(
            call = { getSeriesDetailsUseCase.getSimilarSeries(seriesId) },
            mapper = mediaUiMapper,
            onSuccess = ::onSuccessSimilarSeries,
            onError = ::onError
        )
    }

    private fun onSuccessSimilarSeries(similar: List<MediaUiState>) {
        _state.update { it.copy(seriesSimilarResult = similar, isLoading = false, onError = emptyList()) }
    }

    private fun getSeasonSeries(seriesId: Int) {
        viewModelScope.launch {
            val result = getSeriesDetailsUseCase.getSeasons(seriesId).map {
                seasonUiMapper.map(it)
            }
            _state.update {
                it.copy(seriesSeasonsResult = result)
            }
//            onAddMovieDetailsItemOfNestedView(SeriesItems.Season(_state.value.seriesSeasonsResult))
        }

    }

//    private fun getSeriesReview(seriesId: Int) {
//        viewModelScope.launch {
//            val result = getSeriesDetailsUseCase.getSeriesReview(seriesId)
//            _state.update {
//                it.copy(seriesReviewResult = result.reviews.map(reviewUiMapper::map))
//            }
//            if (result.reviews.isNotEmpty()) {
//                _state.value.seriesReviewResult.forEach {
//                    onAddMovieDetailsItemOfNestedView(SeriesItems.Review(it))
//                }
//                onAddMovieDetailsItemOfNestedView(SeriesItems.ReviewText)
//            }
//            if (result.isMoreThanMax) {
//                onAddMovieDetailsItemOfNestedView(SeriesItems.SeeAllReviews)
//            }
//        }
//
//    }

    fun onChangeRating(value: Float) {
        viewModelScope.launch {
            setRatingUseCase(args.seriesId, value = value)
            _state.update { it.copy(ratingValue = value) }
            sendEvent(SeriesDetailsUiEvent.MessageAppear)
        }
    }

    private fun getLoginStatus() {
        if (!getSessionIdUseCase().isNullOrEmpty()) {
            _state.update { it.copy(isLogin = true) }
            getRatedSeries(args.seriesId)
        }
    }

    private fun getRatedSeries(seriesId: Int) {
        viewModelScope.launch {
            val result = getSeriesRateUseCase(seriesId = seriesId)
            _state.update { it.copy(ratingValue = result) }
//            onAddMovieDetailsItemOfNestedView(SeriesItems.Rating(this@SeriesDetailsViewModel))
        }
    }


//    private fun onAddMovieDetailsItemOfNestedView(items: SeriesItems) {
//        val itemsList = _state.value.seriesItems.toMutableList()
//        itemsList.add(items)
//        _state.update { it.copy(seriesItems = itemsList.toList()) }
//    }

    private fun onError(th: Throwable) {
        val errors = _state.value.onError.toMutableList()
        errors.add(th.message.toString())
        _state.update { it.copy(onError = errors, isLoading = false) }
    }

    override fun onClickBackButton() {
        sendEvent(SeriesDetailsUiEvent.ClickBackButtonEvent)
    }

    override fun onClickPlayTrailer() {
        sendEvent(SeriesDetailsUiEvent.ClickPlayTrailerEvent)
    }

    override fun onclickViewReviews() {
        sendEvent(SeriesDetailsUiEvent.ClickViewReviews)
    }

    override fun onClickFavourite() {
        TODO("Not yet implemented")
    }

    override fun onClickActor(actorID: Int) {
        sendEvent(SeriesDetailsUiEvent.ClickCastEvent(castId = actorID))
    }

    override fun onClickMedia(mediaId: Int) {
        sendEvent(SeriesDetailsUiEvent.ClickSimilarSeriesEvent(seriesId = mediaId))
    }

    override fun onClickSeason(seasonNumber: Int) {
        sendEvent(SeriesDetailsUiEvent.ClickSeasonEvent(seasonNumber = seasonNumber))
    }
}