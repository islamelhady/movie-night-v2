package com.elhady.movies.feature.details.presentation.episodedetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.domain.usecase.auth.CheckIsUserLoggedInUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetCastForEpisodeUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetEpisodeDetailsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetEpisodeVideoUseCase
import com.elhady.movies.core.domain.usecase.tvshow.SetEpisodeRatingUseCase
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.base.toErrorUiState
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.feature.details.presentation.episodedetails.EpisodeDetailsUiEffect.NavigateToCastDetails
import com.elhady.movies.feature.details.presentation.episodedetails.mapper.EpisodeDetailsUiMapper
import com.elhady.movies.feature.details.presentation.episodedetails.mapper.TrailerUiMapper
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.CastUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailsViewModel @Inject constructor(
    private val setEpisodeRatingUseCase: SetEpisodeRatingUseCase,
    private val episodeDetailsUseCase: GetEpisodeDetailsUseCase,
    private val episodeDetailsUiMapper: EpisodeDetailsUiMapper,
    private val castUseCase: GetCastForEpisodeUseCase,
    private val castUiMapper: CastUiMapper,
    private val trailerUiMapper: TrailerUiMapper,
    private val episodeVideoUseCase: GetEpisodeVideoUseCase,
    private val checkIsUserLoggedInUseCase: CheckIsUserLoggedInUseCase,
    savedStateHandle: SavedStateHandle,
    private val stringsRes: StringsRes,
) : BaseViewModel<EpisodeDetailsUiState, EpisodeDetailsUiEffect>(
    EpisodeDetailsUiState()
) {

    private val seriesId: Int =
        checkNotNull(savedStateHandle.get<Int>("seriesId"))

    private val seasonNumber: Int =
        checkNotNull(savedStateHandle.get<Int>("seasonNumber"))

    private val episodeNumber: Int =
        checkNotNull(savedStateHandle.get<Int>("episodeNumber"))

    init {
        _state.update {
            it.copy(
                isLoggedIn = checkIsUserLoggedInUseCase(),
            )
        }

        loadData()
    }

    fun onEvent(event: EpisodeDetailsUiEvent) {
        when (event) {

            EpisodeDetailsUiEvent.BackClicked -> {
                sendEffect(EpisodeDetailsUiEffect.NavigateBack)
            }

            EpisodeDetailsUiEvent.RateClicked -> {
                handleRateClicked()
            }

            is EpisodeDetailsUiEvent.CastClicked -> {
                sendEffect(
                    NavigateToCastDetails(
                        personId = event.personId
                    )
                )
            }

            is EpisodeDetailsUiEvent.PlayFullScreenClicked -> {
                _state.update { it.copy(isPlayerVisible = true) }
            }

            EpisodeDetailsUiEvent.DismissPlayerClicked -> {
                _state.update { it.copy(isPlayerVisible = false) }
            }

            is EpisodeDetailsUiEvent.RatingChanged -> {
                _state.update {
                    it.copy(
                        userRate = event.rating,
                    )
                }
            }

            EpisodeDetailsUiEvent.SubmitRating -> {
                submitRating()
            }

            EpisodeDetailsUiEvent.RetryClicked -> {
                loadData()
            }

            EpisodeDetailsUiEvent.Refresh -> {
                loadData()
            }
        }
    }

    private fun handleRateClicked() {
        if (state.value.isLoggedIn) {
            sendEffect(
                EpisodeDetailsUiEffect.ShowRatingBottomSheet
            )
        } else {
            sendEffect(
                EpisodeDetailsUiEffect.ShowSnackBar(
                    message = stringsRes.notLoggedInToRate
                )
            )
        }
    }

    // region Load Data

    private fun loadData() {
        _state.update {
            it.copy(
                isLoading = true,
                error = null,
            )
        }

        viewModelScope.launch {

            val episodeDetailsDeferred = async(Dispatchers.IO) {
                runCatching {
                    episodeDetailsUiMapper.map(
                        episodeDetailsUseCase(
                            seriesId,
                            seasonNumber,
                            episodeNumber,
                        )
                    )
                }
            }

            val castDeferred = async(Dispatchers.IO) {
                runCatching {
                    castUiMapper.map(
                        castUseCase(
                            seriesId,
                            seasonNumber,
                            episodeNumber,
                        )
                    )
                }
            }

            val videoDeferred = async(Dispatchers.IO) {
                runCatching {
                    trailerUiMapper.map(
                        episodeVideoUseCase(
                            seriesId,
                            seasonNumber,
                            episodeNumber,
                        )
                    )
                }
            }

            val episodeDetailsResult = episodeDetailsDeferred.await()
            val castResult = castDeferred.await()
            val videoResult = videoDeferred.await()

            val firstError = listOf(
                episodeDetailsResult,
                castResult,
                videoResult,
            ).firstOrNull { it.isFailure }

            if (firstError != null) {
                val exception = firstError.exceptionOrNull()

                if (exception is AppException) {
                    onError(exception)
                } else {
                    _state.update {
                        it.copy(
                            error = AppException.Unknown(
                                exception?.message
                                    ?: stringsRes.someThingError
                            ).toErrorUiState(),
                            isLoading = false,
                        )
                    }
                }

                return@launch
            }

            val episodeDetails = episodeDetailsResult.getOrNull()!!
            val cast = castResult.getOrNull()!!
            val trailer = videoResult.getOrNull()!!

            _state.update {
                it.copy(
                    imageUrl = episodeDetails.imageUrl,
                    episodeName = episodeDetails.episodeName,
                    episodeNumber = episodeDetails.episodeNumber,
                    seasonNumber = episodeDetails.seasonNumber,
                    episodeRate = episodeDetails.episodeRate,
                    episodeOverview = episodeDetails.episodeOverview,
                    voteAverage = episodeDetails.voteAverage,
                    cast = cast,
                    trailerKey = trailer.videoKey,
                    isLoading = false,
                    error = null,
                )
            }
        }
    }

    // endregion

    // region Rating

    private fun submitRating() {
        tryToExecute(
            call = {
                setEpisodeRatingUseCase(
                    seriesId,
                    seasonNumber,
                    episodeNumber,
                    state.value.userRate,
                )
            },
            onSuccess = {
                sendEffect(
                    EpisodeDetailsUiEffect.ShowSnackBar(
                        stringsRes.ratingAddSuccessFully
                    )
                )
            },
            onError = {
                sendEffect(
                    EpisodeDetailsUiEffect.ShowSnackBar(
                        stringsRes.someThingErrorWhenAddRating
                    )
                )
            },
        )
    }

    // endregion

    // region Error

    private fun onError(error: AppException) {
        val errorUiState = error.toErrorUiState()

        _state.update {
            it.copy(
                error = errorUiState,
                isLoading = false,
            )
        }
    }

    // endregion
}
