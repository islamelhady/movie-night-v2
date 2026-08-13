package com.elhady.movies.feature.details.presentation.seasondetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.details.BR
import com.elhady.movies.feature.details.R
import com.elhady.movies.feature.details.databinding.FragmentSeasonDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewEpisodes.adapter = seasonDetailsAdapter
        collectChange()
    }

    private fun collectChange() {
        collectFlow(flow = viewModel.state) { state ->
                val seasonDetailsItems = mutableListOf(
                    SeasonDetailsItem.OverviewItem(state.overview, state.episodes.isEmpty())
                ) + state.episodes.map { SeasonDetailsItem.EpisodeItem(it) }
                seasonDetailsAdapter.setItems(seasonDetailsItems)
            }
        }


    override fun onEffect(effect: SeasonDetailsUiEvent) {
        when (effect) {
            is SeasonDetailsUiEvent.NavigateToEpisodeDetails -> {
                navigator.navigateToEpisodeDetails(
                    effect.seriesId,
                    effect.seasonNumber,
                    effect.episodeId
                )
            }

            SeasonDetailsUiEvent.NavigateBack -> navigator.navigateBack()
            is SeasonDetailsUiEvent.ShowSnackBar -> showSnackBar(effect.messages)
        }
    }
}
