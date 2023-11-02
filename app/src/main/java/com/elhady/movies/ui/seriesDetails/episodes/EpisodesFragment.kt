package com.elhady.movies.ui.seriesDetails.episodes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentEpisodesBinding
import com.elhady.movies.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class EpisodesFragment : BaseFragment<FragmentEpisodesBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_episodes
    override val viewModel: EpisodesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerEpisode.adapter = EpisodeAdapter(mutableListOf(), viewModel)

    }

}