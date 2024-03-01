package com.elhady.viewmodel.seriesDetails.episodes

import androidx.lifecycle.SavedStateHandle
import com.elhady.base.BaseViewModel
import com.elhady.usecase.seriesDetails.GetSeasonsEpisodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getSeasonsEpisodesUseCase: GetSeasonsEpisodesUseCase,
    private val episodeUiMapper: EpisodeUiMapper
) : BaseViewModel<EpisodesUiState, EpisodesInteraction>(EpisodesUiState()), BaseInteractionListener {

    val args = EpisodesFragmentArgs.fromSavedStateHandle(state)


    init {
        getData()
    }
    override fun getData() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            call = { getSeasonsEpisodesUseCase(seriesId = args.seriesId, seasonNumber = args.seasonNumber)},
            onSuccess = ::onSuccessEpisodes,
            mapper = episodeUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessEpisodes(seasonEpisodes: List<SeasonEpisodesUiState>) {
        _state.update { it.copy(isLoading = false, onErrors = emptyList(), seasonsEpisodesResult = seasonEpisodes) }
    }

    private fun onError(throwable: Throwable){
        _state.update { it.copy(isLoading = false, onErrors = listOf(throwable.message.toString())) }
    }
}