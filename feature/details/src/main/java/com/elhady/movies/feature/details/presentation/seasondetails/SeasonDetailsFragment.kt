package com.elhady.movies.feature.details.presentation.seasondetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.details.BR
import com.elhady.movies.feature.details.R
import com.elhady.movies.feature.details.databinding.FragmentSeasonDetailsBinding
import com.elhady.movies.feature.details.presentation.episodedetails.EpisodeListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SeasonDetailsFragment :
    BaseFragment<FragmentSeasonDetailsBinding, SeasonDetailsUiState, SeasonDetailsUiEffect>(),
    SeasonDetailsListener, EpisodeListener {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int = R.layout.fragment_season_details
    override val viewModel: SeasonDetailsViewModel by viewModels()

    private val seasonDetailsAdapter: SeasonDetailsAdapter by lazy {
        SeasonDetailsAdapter(
            mutableListOf(),
            this
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.listener = this
        binding.recyclerViewEpisodes.adapter = seasonDetailsAdapter
    }

    override fun render(state: SeasonDetailsUiState) {
        binding.state = state
        val items = buildList {
            add(
                SeasonDetailsItem.OverviewItem(
                    overview = state.overview,
                    isEmptyEpisodes = state.episodes.isEmpty(),
                )
            )

            addAll(
                state.episodes.map {
                    SeasonDetailsItem.EpisodeItem(it)
                }
            )
        }

        seasonDetailsAdapter.setItems(items)
    }

    override fun onEffect(
        effect: SeasonDetailsUiEffect,
    ) {
        when (effect) {

            SeasonDetailsUiEffect.NavigateBack -> {
                navigator.navigateBack()
            }

            is SeasonDetailsUiEffect.NavigateToEpisodeDetails -> {
                navigator.navigateToEpisodeDetails(
                    effect.seriesId,
                    effect.seasonNumber,
                    effect.episodeId,
                )
            }

            is SeasonDetailsUiEffect.ShowSnackBar -> {
                showSnackBar(effect.message)
            }
        }
    }

    override fun onClickBack() {
        viewModel.onEvent(SeasonDetailsUiEvent.BackClicked)
    }

    override fun onClickRetry() {
        viewModel.onEvent(SeasonDetailsUiEvent.RetryClicked)
    }

    override fun onClickEpisode(id: Int) {
        viewModel.onEvent(SeasonDetailsUiEvent.EpisodeClicked(id))
    }

}
