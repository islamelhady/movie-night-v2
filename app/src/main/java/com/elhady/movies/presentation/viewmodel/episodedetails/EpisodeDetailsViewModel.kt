package com.elhady.movies.presentation.viewmodel.episodedetails

import androidx.lifecycle.SavedStateHandle
import com.elhady.movies.presentation.viewmodel.search.mappers.PeopleUiMapper
import com.elhady.movies.core.bases.BaseViewModel
import com.elhady.movies.core.bases.StringsRes
import com.elhady.movies.core.domain.entities.RatingEpisodeDetailsStatusEntity
import com.elhady.movies.core.domain.usecase.usecase.episodedetails.GetCastForEpisodeUseCase
import com.elhady.movies.core.domain.usecase.usecase.episodedetails.GetEpisodeDetailsUseCase
import com.elhady.movies.core.domain.usecase.usecase.episodedetails.GetEpisodeVideoUseCase
import com.elhady.movies.core.domain.usecase.usecase.episodedetails.SetEpisodeRatingUseCase
import com.elhady.movies.core.domain.usecase.usecase.common.CheckIsLoginOrNotUseCase
import com.elhady.movies.presentation.viewmodel.common.listener.PeopleListener
import com.elhady.movies.presentation.viewmodel.common.model.PeopleUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailsViewModel @Inject constructor(
    private val setEpisodeRatingUseCase: SetEpisodeRatingUseCase,
    private val episodeDetailsUseCase: GetEpisodeDetailsUseCase,
    private val episodeDetailsUiMapper: EpisodeDetailsUiMapper,
    private val castUseCase: GetCastForEpisodeUseCase,
    private val peopleUiMapper: PeopleUiMapper,
    private val trailerUiMapper: TrailerUiMapper,
    private val episodeVideoUseCase: GetEpisodeVideoUseCase,
    private val checkIsLoggedInOrNotUseCase: CheckIsLoginOrNotUseCase,
    savedStateHandle: SavedStateHandle,
    private val stringsRes: StringsRes
) : BaseViewModel<EpisodeDetailsUiState, EpisodeDetailsUiEvent>(EpisodeDetailsUiState()),
    EpisodeDetailsListener, PeopleListener {
    private val seriesId = savedStateHandle.get<Int>("seriesId") ?: 454
    private val seasonNumber = savedStateHandle.get<Int>("seasonNumber") ?: 1
    private val episodeNumber = savedStateHandle.get<Int>("episodeNumber") ?: 1

    init {
        _state.update { it.copy(isLoading = true, isLoggedIn = checkIsLoggedInOrNotUseCase()) }
        getData(seriesId, seasonNumber, episodeNumber)
    }

    private fun getData(seriesId: Int, seasonNumber: Int, episodeNumber: Int) {
        _state.update { it.copy(isLoading = true) }
        getEpisodeDetailsData(seriesId, seasonNumber, episodeNumber)
        getCastData(seriesId, seasonNumber, episodeNumber)
        getEpisodeVideo(seriesId, seasonNumber, episodeNumber)
    }

    /// region refresh
    fun refresh() {
        _state.update { it.copy(refreshing = true, onErrors = emptyList(), isLoading = true) }
        getData(seriesId, seasonNumber, episodeNumber)
        _state.update { it.copy(refreshing = false, onErrors = emptyList(), isLoading = false) }
    }
    /// endregion

    /// region episode data
    private fun getEpisodeDetailsData(seriesId: Int, seasonNumber: Int, episodeNumber: Int) {
        tryToExecute(
            call = { episodeDetailsUseCase(seriesId, seasonNumber, episodeNumber) },
            mapper = episodeDetailsUiMapper,
            onSuccess = ::onSuccessEpisodeDetail,
            onError = ::onError
        )
    }

    private fun onSuccessEpisodeDetail(episodeDetails: EpisodeDetailsUiState) {
        _state.update {
            it.copy(
                imageUrl = episodeDetails.imageUrl,
                episodeName = episodeDetails.episodeName,
                episodeNumber = episodeDetails.episodeNumber,
                seasonNumber = episodeDetails.seasonNumber,
                episodeRate = episodeDetails.episodeRate,
                episodeOverview = episodeDetails.episodeOverview,
                voteAverage = episodeDetails.voteAverage,
                onErrors = emptyList(),
                isLoading = false,
                refreshing = false,
            )
        }
    }
    /// endregion

    ///region video
    private fun getEpisodeVideo(seriesId: Int, seasonNumber: Int, episodeNumber: Int) {
        tryToExecute(
            call = { episodeVideoUseCase(seriesId, seasonNumber, episodeNumber) },
            onSuccess = ::onSuccessEpisodeVideo,
            mapper = trailerUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessEpisodeVideo(trailerUiState: TrailerUiState) {
        _state.update {
            it.copy(
                trailerKey = trailerUiState.videoKey,
                refreshing = false, onErrors = emptyList()
            )
        }
    }

    /// endregion

    /// region set rating
    fun setRating() {
        tryToExecute(
            call = {
                setEpisodeRatingUseCase(
                    seriesId,
                    seasonNumber,
                    episodeNumber,
                    _state.value.userRate
                )
            },
            onSuccess = ::onRatingSuccess,
            onError = ::onRatingError
        )
    }

    private fun onRatingSuccess(episodeRateStatusEntity: RatingEpisodeDetailsStatusEntity) {
        sendEvent(EpisodeDetailsUiEvent.SubmitRating(stringsRes.ratingAddSuccessFully))
    }

    private fun onRatingError(th: Throwable) {
        sendEvent(EpisodeDetailsUiEvent.SubmitRating(stringsRes.someThingErrorWhenAddRating))
    }

    fun updateRatingState(rate: Float) {
        _state.update { it.copy(userRate = rate, onErrors = emptyList()) }
    }

    /// endregion

    /// region cast data
    private fun getCastData(seriesId: Int, seasonNumber: Int, episodeNumber: Int) {
        tryToExecute(
            call = { castUseCase(seriesId, seasonNumber, episodeNumber) },
            mapper = peopleUiMapper,
            onSuccess = ::onSuccessCast,
            onError = ::onError
        )
    }

    private fun onSuccessCast(cast: List<PeopleUIState>) {
        _state.update { it.copy(cast = cast, refreshing = false, onErrors = emptyList()) }
    }

    /// endregion

    ///  region error

    private fun onError(th: Throwable) {
        val errorMessage = th.message ?: stringsRes.someThingError
        _state.update {
            it.copy(
                onErrors = listOf(errorMessage),
                isLoading = false,
                refreshing = false,
                trailerKey = ""
            )
        }
    }
    /// endregion

    /// region event
    override fun clickToBack() {
        sendEvent(EpisodeDetailsUiEvent.ClickToBack)
    }

    override fun clickToRate(episodeId: Int) {
        sendEvent(EpisodeDetailsUiEvent.ClickToRate(episodeId))
    }

    override fun clickToPlayFullScreen(videoKey: String) {
        sendEvent(EpisodeDetailsUiEvent.ClickToPlayFullScreen(videoKey))
    }

    override fun onClickPeople(id: Int) {
        sendEvent(EpisodeDetailsUiEvent.ClickCast(id))
    }
    /// endregion
}