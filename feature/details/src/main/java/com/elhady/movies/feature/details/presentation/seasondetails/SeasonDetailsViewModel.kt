package com.elhady.movies.feature.details.presentation.seasondetails

import androidx.lifecycle.SavedStateHandle
import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.domain.usecase.tvshow.GetSeasonDetailsUseCase
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.base.toErrorUiState
import com.elhady.movies.feature.details.presentation.seasondetails.mapper.SeasonDetailsUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SeasonDetailsViewModel @Inject constructor(
    private val getSeasonDetailsUseCase: GetSeasonDetailsUseCase,
    private val seasonDetailsUiMapper: SeasonDetailsUiMapper,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SeasonDetailsUiState, SeasonDetailsUiEffect>(
    SeasonDetailsUiState()
) {

    private val seriesId: Int =
        checkNotNull(savedStateHandle.get<Int>(SERIES_ID))

    private val seasonNumber: Int =
        checkNotNull(savedStateHandle.get<Int>(SEASON_NUMBER))

    init {
        getData()
    }

    fun onEvent(event: SeasonDetailsUiEvent) {
        when (event) {

            SeasonDetailsUiEvent.BackClicked -> {
                sendEffect(
                    SeasonDetailsUiEffect.NavigateBack
                )
            }

            SeasonDetailsUiEvent.RetryClicked -> {
                getData()
            }

            is SeasonDetailsUiEvent.EpisodeClicked -> {
                sendEffect(
                    SeasonDetailsUiEffect.NavigateToEpisodeDetails(
                        episodeId = event.episodeId,
                        seriesId = seriesId,
                        seasonNumber = seasonNumber,
                    )
                )
            }
        }
    }

    private fun getData() {
        _state.update {
            it.copy(
                isLoading = true,
                error = null,
            )
        }

        getSeasonDetails()
    }

    private fun getSeasonDetails() {
        tryToExecute(
            call = {
                getSeasonDetailsUseCase(
                    seriesId = seriesId,
                    seasonNumber = seasonNumber,
                )
            },
            mapper = seasonDetailsUiMapper,
            onSuccess = ::onSuccessSeasonDetails,
            onError = ::onError,
        )
    }

    private fun onSuccessSeasonDetails(
        seasonDetails: SeasonDetailsUiState,
    ) {
        _state.update {
            it.copy(
                id = seasonDetails.id,
                name = seasonDetails.name,
                overview = seasonDetails.overview,
                episodes = seasonDetails.episodes,
                isLoading = false,
                error = null,
            )
        }
    }

    private fun onError(error: AppException) {
        val error = error.toErrorUiState()

        _state.update {
            it.copy(
                isLoading = false,
                error = error,
            )
        }

        sendEffect(
            SeasonDetailsUiEffect.ShowSnackBar(
                message = error.messageRes.toString()
            )
        )
    }

    companion object{
        const val SEASON_NUMBER = "seasonNumber"
        const val SERIES_ID = "seriesId"
    }
}
