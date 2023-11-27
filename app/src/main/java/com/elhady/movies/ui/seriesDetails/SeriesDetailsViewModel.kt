package com.elhady.movies.ui.seriesDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
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
import com.elhady.movies.ui.movieDetails.DetailsInteractionListener
import com.elhady.movies.ui.seriesDetails.seriesUiMapper.SeasonUiMapper
import com.elhady.movies.ui.seriesDetails.seriesUiMapper.SeriesDetailsUiMapper
import com.elhady.movies.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        getSeriesReview(args.seriesId)
        getLoginStatus()
    }

    private suspend fun addWatchHistory(series: SeriesDetails) {
        insertWatchSeriesUseCase(series)
    }

    private fun getTVShowDetails(tvShowId: Int) {
        viewModelScope.launch {
            val result = getSeriesDetailsUseCase.getSeriesDetails(tvShowId)
            _state.update {
                it.copy(
                    seriesDetailsResult = seriesDetailsUiMapper.map(result)
                )
            }
            onAddMovieDetailsItemOfNestedView(SeriesItems.Header(_state.value.seriesDetailsResult))
            addWatchHistory(result)
        }
    }

    private fun getSeriesCast(tvShowId: Int) {
        viewModelScope.launch {
            val result = getSeriesDetailsUseCase.getSeriesCast(tvShowId).map {
                actorUiMapper.map(it)
            }
            _state.update {
                it.copy(seriesCastResult = result)
            }
            onAddMovieDetailsItemOfNestedView(SeriesItems.Cast(_state.value.seriesCastResult))
        }
    }

    private fun getSimilarSeries(seriesId: Int) {
        viewModelScope.launch {
            val result = getSeriesDetailsUseCase.getSimilarSeries(seriesId).map {
                mediaUiMapper.map(it)
            }
            _state.update {
                it.copy(seriesSimilarResult = result)
            }
            onAddMovieDetailsItemOfNestedView(SeriesItems.Similar(_state.value.seriesSimilarResult))
        }
    }

    private fun getSeasonSeries(seriesId: Int) {
        viewModelScope.launch {
            val result = getSeriesDetailsUseCase.getSeasons(seriesId).map {
                seasonUiMapper.map(it)
            }
            _state.update {
                it.copy(seriesSeasonsResult = result)
            }
            onAddMovieDetailsItemOfNestedView(SeriesItems.Season(_state.value.seriesSeasonsResult))
        }

    }

    private fun getSeriesReview(seriesId: Int) {
        viewModelScope.launch {
            val result = getSeriesDetailsUseCase.getSeriesReview(seriesId)
            _state.update {
                it.copy(seriesReviewResult = result.reviews.map(reviewUiMapper::map))
            }
            if (result.reviews.isNotEmpty()) {
                _state.value.seriesReviewResult.forEach {
                    onAddMovieDetailsItemOfNestedView(SeriesItems.Review(it))
                }
                onAddMovieDetailsItemOfNestedView(SeriesItems.ReviewText)
            }
            if (result.isMoreThanMax) {
                onAddMovieDetailsItemOfNestedView(SeriesItems.SeeAllReviews)
            }
        }

    }

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
            onAddMovieDetailsItemOfNestedView(SeriesItems.Rating(this@SeriesDetailsViewModel))
        }
    }


    private fun onAddMovieDetailsItemOfNestedView(items: SeriesItems) {
        val itemsList = _state.value.seriesItems.toMutableList()
        itemsList.add(items)
        _state.update { it.copy(seriesItems = itemsList.toList()) }
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