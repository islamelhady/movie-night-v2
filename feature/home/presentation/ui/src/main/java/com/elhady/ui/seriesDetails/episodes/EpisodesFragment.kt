package com.elhady.ui.seriesDetails.episodes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.base.BaseFragment
import com.elhady.ui.R
import com.elhady.ui.databinding.FragmentEpisodesBinding
import com.elhady.viewmodel.seriesDetails.episodes.EpisodesUiState
import com.elhady.viewmodel.seriesDetails.episodes.EpisodesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EpisodesFragment : BaseFragment<FragmentEpisodesBinding, EpisodesUiState, EpisodesInteraction>() {
    override val layoutIdFragment: Int = R.layout.fragment_episodes
    override val viewModel: EpisodesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(visibility = true, title = viewModel.args.seasonName)
        binding.recyclerEpisode.adapter = EpisodeAdapter(mutableListOf(), viewModel)

    }

    override fun onEvent(event: EpisodesInteraction) {
        TODO("Not yet implemented")
    }

}