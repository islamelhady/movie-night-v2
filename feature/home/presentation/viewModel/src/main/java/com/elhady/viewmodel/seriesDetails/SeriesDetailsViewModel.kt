package com.elhady.viewmodel.seriesDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.base.BaseViewModel
import com.elhady.usecase.GetSessionIdUseCase
import com.elhady.usecase.seriesDetails.GetSeriesDetailsUseCase
import com.elhady.usecase.seriesDetails.GetSeriesRateUseCase
import com.elhady.usecase.seriesDetails.InsertWatchSeriesUseCase
import com.elhady.usecase.seriesDetails.SetRatingUseCase
import com.elhady.viewmodel.mappers.ActorUiMapper
import com.elhady.viewmodel.mappers.MediaUiMapper
import com.elhady.viewmodel.mappers.ReviewUiMapper
import com.elhady.viewmodel.models.ActorUiState
import com.elhady.viewmodel.models.MediaUiState
import com.elhady.viewmodel.movieDetails.DetailsInteractionListener
import com.elhady.viewmodel.seriesDetails.seriesUiMapper.SeasonUiMapper
import com.elhady.viewmodel.seriesDetails.seriesUiMapper.SeriesDetailsUiMapper
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
        _state.update { it.copy(isLoading = true, onErrors = emptyList()) }
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
        _state.update { it.copy(seriesDetailsResult = seriesDetailsUiMapper.map(details), isLoading = false, onErrors = emptyList()) }
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
        _state.update { it.copy(seriesCastResult = cast, isLoading = false, onErrors = emptyList()) }
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
        _state.update { it.copy(seriesSimilarResult = similar, isLoading = false, onErrors = emptyList()) }
    }

    private fun getSeasonSeries(seriesId: Int) {
        tryToExecute(
            call = { getSeriesDetailsUseCase.getSeasons(seriesId) },
            mapper = seasonUiMapper,
            onSuccess = ::onSuccessSeasonSeries,
            onError = ::onError
        )
    }

    private fun onSuccessSeasonSeries(season: List<SeasonUiState>) {
        _state.update { it.copy(seriesSeasonsResult = season, isLoading = false, onErrors = emptyList()) }
    }

    private fun getSeriesReview(seriesId: Int) {
        tryToExecute(
            call = { getSeriesDetailsUseCase.getSeriesReview(seriesId) },
            onSuccess = ::onSuccessSeriesReview,
            onError = ::onError
        )
    }

    private fun onSuccessSeriesReview(review: MediaDetailsReview) {
        val result = reviewUiMapper.map(review.reviews)
        _state.update { it.copy(seriesReviewResult = result, isLoading = false, onErrors = emptyList()) }
    }

    fun onChangeRating(value: Float) {
        tryToExecute(
            call = { setRatingUseCase(seriesId = args.seriesId, value = value) },
            onSuccess = ::onSuccessRating,
            onError = ::onError
        )
    }

    private fun onSuccessRating(status: RatingStatus) {
        sendEvent(SeriesDetailsUiEvent.MessageAppear(status.statusMessage))
    }

    private fun getLoginStatus() {
        if (!getSessionIdUseCase().isNullOrEmpty()) {
            _state.update { it.copy(isLogin = true) }
            getRatedSeries(args.seriesId)
        }
    }

    private fun getRatedSeries(seriesId: Int) {
        tryToExecute(
            call = { getSeriesRateUseCase(seriesId) },
            onSuccess = ::onSuccessRatedSeries,
            onError = ::onError
        )
    }

    private fun onSuccessRatedSeries(value: Float) {
        _state.update { it.copy(ratingValue = value, isLoading = false, onErrors = emptyList()) }
    }

    private fun onError(th: Throwable) {
        val errors = _state.value.onErrors.toMutableList()
        errors.add(th.message.toString())
        _state.update { it.copy(onErrors = errors, isLoading = false) }
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

    override fun onClickSeason(seasonNumber: Int, seasonName: String) {
        sendEvent(SeriesDetailsUiEvent.ClickSeasonEvent(seasonNumber = seasonNumber, seasonName = seasonName))
    }
}