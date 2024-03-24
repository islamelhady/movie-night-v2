package com.elhady.movies.presentation.ui.seasondetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseFragment
import com.elhady.movies.databinding.FragmentSeasonDetailsBinding
import com.elhady.movies.presentation.viewmodel.seasondetails.SeasonDetailsUiEvent
import com.elhady.movies.presentation.viewmodel.seasondetails.SeasonDetailsUiState
import com.elhady.movies.presentation.viewmodel.seasondetails.SeasonDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SeasonDetailsFragment
    : BaseFragment<FragmentSeasonDetailsBinding, SeasonDetailsUiState, SeasonDetailsUiEvent>() {

    override val layoutIdFragment: Int = R.layout.fragment_season_details
    override val viewModel: SeasonDetailsViewModel by viewModels()

    private val seasonDetailsAdapter: SeasonDetailsAdapter
            by lazy { SeasonDetailsAdapter(mutableListOf(), viewModel) }
//    private val args: SeasonDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewEpisodes.adapter = seasonDetailsAdapter
        collectChange()
    }

    private fun collectChange() {
        collectLatest {
            viewModel.state.collectLatest { state ->
                val seasonDetailsItems = mutableListOf(
                    SeasonDetailsItem.OverviewItem(state.overview, state.episodes.isEmpty())
                ) + state.episodes.map { SeasonDetailsItem.EpisodeItem(it) }
                seasonDetailsAdapter.setItems(seasonDetailsItems)
            }
        }
    }

    override fun onEvent(event: SeasonDetailsUiEvent) {
        when (event) {
            is SeasonDetailsUiEvent.NavigateToEpisodeDetails -> {
                findNavController().navigate(
                    SeasonDetailsFragmentDirections.actionSeasonDetailsFragmentToEpisodeDetailsFragment(
                        seriesId = event.seriesId,
                        seasonNumber = event.seasonNumber,
                        episodeNumber = event.episodeId
                    )
                )
            }

            SeasonDetailsUiEvent.NavigateBack -> findNavController().popBackStack()
            is SeasonDetailsUiEvent.ShowSnackBar -> showSnackBar(event.messages)
        }
    }
}