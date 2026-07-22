package com.elhady.movies.feature.details.presentation.ui.seasondetails

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.elhady.movies.feature.details.BR
import com.elhady.movies.feature.details.R
import com.elhady.movies.core.common.bases.BaseFragment
import com.elhady.movies.feature.details.databinding.FragmentSeasonDetailsBinding
import com.elhady.movies.feature.details.presentation.seasondetails.SeasonDetailsUiEvent
import com.elhady.movies.feature.details.presentation.seasondetails.SeasonDetailsUiState
import com.elhady.movies.feature.details.presentation.seasondetails.SeasonDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SeasonDetailsFragment
    : BaseFragment<FragmentSeasonDetailsBinding, SeasonDetailsUiState, SeasonDetailsUiEvent>() {

    override val layoutIdFragment: Int = R.layout.fragment_season_details
    override val viewModel: SeasonDetailsViewModel by viewModels()
    override val viewModelVariableId: Int = BR.viewModel

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
                val request = NavDeepLinkRequest.Builder
                    .fromUri(Uri.parse("movie://episode_details/${event.seriesId}/${event.seasonNumber}/${event.episodeId}"))
                    .build()
                findNavController().navigate(request)
            }

            SeasonDetailsUiEvent.NavigateBack -> findNavController().popBackStack()
            is SeasonDetailsUiEvent.ShowSnackBar -> showSnackBar(event.messages)
        }
    }
}
