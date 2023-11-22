package com.elhady.movies.ui.seriesDetails.episodes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.seriesDetails.GetSeasonsEpisodesUseCase
import com.elhady.movies.ui.base.BaseInteractionListener
import com.elhady.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getSeasonsEpisodesUseCase: GetSeasonsEpisodesUseCase,
    private val episodeUiMapper: EpisodeUiMapper
) : BaseViewModel<EpisodesUiState>(EpisodesUiState()), BaseInteractionListener {

    private val args = EpisodesFragmentArgs.fromSavedStateHandle(state)

    private val _episodeUiState = MutableStateFlow(EpisodesUiState())
    val episodeUiState = _episodeUiState.asStateFlow()


    init {
        getData()
    }
    override fun getData() {
        viewModelScope.launch {
            val result =getSeasonsEpisodesUseCase(seriesId = args.seriesId, seasonNumber = args.seasonNumber).map {
                episodeUiMapper.map(it)
            }

            _episodeUiState.update {
                it.copy(
                seasonsEpisodesResult = result
                )
            }
        }
    }

}