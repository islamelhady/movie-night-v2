package com.elhady.movies.feature.details.presentation.seasondetails

import androidx.lifecycle.SavedStateHandle
import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.domain.usecase.tvshow.GetSeasonDetailsUseCase
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.base.toErrorUiState
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.feature.details.presentation.seasondetails.mapper.SeasonDetailsUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SeasonDetailsViewModel @Inject constructor(
    private val getSeasonDetailsUseCase: GetSeasonDetailsUseCase,
    private val seasonDetailsUiMapper: SeasonDetailsUiMapper,
    private val stringsRes: StringsRes,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SeasonDetailsUiState, SeasonDetailsUiEffect>(
    SeasonDetailsUiState()
) {

    private val seriesId: Int? = savedStateHandle.get<Int>(SERIES_ID)
    private val seasonNumber: Int? = savedStateHandle.get<Int>(SEASON_NUMBER)

    init {
        if (seriesId != null && seasonNumber != null) {
            getData()
        } else {
            _state.update {
                it.copy(
                    isLoading = false,
                    error = com.elhady.movies.core.ui.base.ErrorUiState.EmptyResponse
                )
            }
        }
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
                        seriesId = seriesId!!,
                        seasonNumber = seasonNumber!!,
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
                    seriesId = seriesId!!,
                    seasonNumber = seasonNumber!!,
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
        val errorUiState = error.toErrorUiState()

        _state.update {
            it.copy(
                isLoading = false,
                error = errorUiState,
            )
        }

        sendEffect(
            SeasonDetailsUiEffect.ShowSnackBar(
                message = stringsRes.someThingError // Using stringsRes
            )
        )
    }

    companion object{
        const val SEASON_NUMBER = "seasonNumber"
        const val SERIES_ID = "seriesId"
    }
}
