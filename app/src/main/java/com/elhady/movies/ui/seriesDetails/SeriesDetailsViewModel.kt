package com.elhady.movies.ui.seriesDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.models.SeriesDetails
import com.elhady.movies.domain.usecases.seriesDetails.GetSeriesDetailsUseCase
import com.elhady.movies.domain.usecases.seriesDetails.InsertWatchSeriesUseCase
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
    private val actorUiMapper: ActorUiMapper,
    private val mediaUiMapper: MediaUiMapper,
    private val seasonUiMapper: SeasonUiMapper,
    private val reviewUiMapper: ReviewUiMapper
) : BaseViewModel(), DetailsInteractionListener, ActorInteractionListener, MediaInteractionListener,
    SeasonInteractionListener {

    val args = SeriesDetailsFragmentArgs.fromSavedStateHandle(state)

    private val _seriesUiState = MutableStateFlow(SeriesDetailsUiState())
    val seriesUiState = _seriesUiState.asStateFlow()

    private val _seriesUiEvent = MutableStateFlow<Event<SeriesDetailsUiEvent>?>(null)
    val seriesUiEvent = _seriesUiEvent.asStateFlow()

    init {
        getData()
    }

    override fun getData() {
        getTVShowDetails(args.seriesId)
        getSeriesCast(args.seriesId)
        getSimilarSeries(args.seriesId)
        getSeasonSeries(args.seriesId)
        getSeriesReview(args.seriesId)
    }

    suspend fun addWatchHistory(series: SeriesDetails){
        insertWatchSeriesUseCase(series)
    }

    private fun getTVShowDetails(tvShowId: Int) {
        viewModelScope.launch {
            val result = getSeriesDetailsUseCase.getSeriesDetails(tvShowId)
            _seriesUiState.update {
                it.copy(
                    seriesDetailsResult =  seriesDetailsUiMapper.map(result)
                )
            }
            onAddMovieDetailsItemOfNestedView(SeriesItems.Header(_seriesUiState.value.seriesDetailsResult))
            addWatchHistory(result)
        }
    }

    private fun getSeriesCast(tvShowId: Int) {
        viewModelScope.launch {
            val result = getSeriesDetailsUseCase.getSeriesCast(tvShowId).map {
                actorUiMapper.map(it)
            }
            _seriesUiState.update {
                it.copy(seriesCastResult = result)
            }
            onAddMovieDetailsItemOfNestedView(SeriesItems.Cast(_seriesUiState.value.seriesCastResult))
        }
    }

    private fun getSimilarSeries(seriesId: Int) {
        viewModelScope.launch {
            val result = getSeriesDetailsUseCase.getSimilarSeries(seriesId).map {
                mediaUiMapper.map(it)
            }
            _seriesUiState.update {
                it.copy(seriesSimilarResult = result)
            }
            onAddMovieDetailsItemOfNestedView(SeriesItems.Similar(_seriesUiState.value.seriesSimilarResult))
        }
    }

    private fun getSeasonSeries(seriesId: Int) {
        viewModelScope.launch {
            val result = getSeriesDetailsUseCase.getSeasons(seriesId).map {
                seasonUiMapper.map(it)
            }
            _seriesUiState.update {
                it.copy(seriesSeasonsResult = result)
            }
            onAddMovieDetailsItemOfNestedView(SeriesItems.Season(_seriesUiState.value.seriesSeasonsResult))
        }

    }

    private fun getSeriesReview(seriesId: Int) {
        viewModelScope.launch {
            val result = getSeriesDetailsUseCase.getSeriesReview(seriesId)
            _seriesUiState.update {
                it.copy(seriesReviewResult = result.reviews.map(reviewUiMapper::map))
            }
            if (result.reviews.isNotEmpty()) {
                _seriesUiState.value.seriesReviewResult.forEach {
                    onAddMovieDetailsItemOfNestedView(SeriesItems.Review(it))
                }
                onAddMovieDetailsItemOfNestedView(SeriesItems.ReviewText)
            }
            if (result.isMoreThanMax) {
                onAddMovieDetailsItemOfNestedView(SeriesItems.SeeAllReviews)
            }
        }

    }

    private fun setReview(seeAllReviews: Boolean) {
        _seriesUiState.value.seriesReviewResult.forEach {
            onAddMovieDetailsItemOfNestedView(SeriesItems.Review(it))
        }
        onAddMovieDetailsItemOfNestedView(SeriesItems.ReviewText)
        if (seeAllReviews) {
            onAddMovieDetailsItemOfNestedView(SeriesItems.ReviewText)
        }

    }


    private fun onAddMovieDetailsItemOfNestedView(items: SeriesItems) {
        val itemsList = _seriesUiState.value.seriesItems.toMutableList()
        itemsList.add(items)
        _seriesUiState.update { it.copy(seriesItems = itemsList.toList()) }
    }

    override fun onClickBackButton() {
        _seriesUiEvent.update {
            Event(SeriesDetailsUiEvent.ClickBackButtonEvent)
        }
    }

    override fun onClickPlayTrailer() {
        _seriesUiEvent.update {
            Event(SeriesDetailsUiEvent.ClickPlayTrailerEvent)
        }
    }

    override fun onclickViewReviews() {
        _seriesUiEvent.update {
            Event(SeriesDetailsUiEvent.ClickViewReviews)
        }
    }

    override fun onClickActor(actorID: Int) {
        _seriesUiEvent.update {
            Event(SeriesDetailsUiEvent.ClickCastEvent(castId = actorID))
        }
    }

    override fun onClickMedia(mediaId: Int) {
        _seriesUiEvent.update {
            Event(SeriesDetailsUiEvent.ClickSimilarSeriesEvent(seriesId = mediaId))
        }
    }

    override fun onClickSeason(seasonNumber: Int) {
        _seriesUiEvent.update {
            Event(SeriesDetailsUiEvent.ClickSeasonEvent(seasonNumber = seasonNumber))
        }
    }
}