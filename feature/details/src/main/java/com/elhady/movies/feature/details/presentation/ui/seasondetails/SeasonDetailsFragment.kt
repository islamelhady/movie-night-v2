package com.elhady.movies.feature.details.presentation.ui.seasondetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.feature.details.BR
import com.elhady.movies.feature.details.R
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.feature.details.databinding.FragmentSeasonDetailsBinding
import com.elhady.movies.feature.details.presentation.seasondetails.SeasonDetailsUiEvent
import com.elhady.movies.feature.details.presentation.seasondetails.SeasonDetailsUiState
import com.elhady.movies.feature.details.presentation.seasondetails.SeasonDetailsViewModel
import com.elhady.movies.core.ui.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class SeasonDetailsFragment
    : BaseFragment<FragmentSeasonDetailsBinding, SeasonDetailsUiState, SeasonDetailsUiEvent>() {

    @Inject
    lateinit var navigator: Navigator

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
                navigator.navigateToEpisodeDetails(
                    event.seriesId,
                    event.seasonNumber,
                    event.episodeId
                )
            }

            SeasonDetailsUiEvent.NavigateBack -> navigator.navigateBack()
            is SeasonDetailsUiEvent.ShowSnackBar -> showSnackBar(event.messages)
        }
    }
}
