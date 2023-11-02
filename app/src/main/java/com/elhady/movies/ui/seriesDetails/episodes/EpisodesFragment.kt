package com.elhady.movies.ui.seriesDetails.episodes

import androidx.fragment.app.viewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentEpisodesBinding
import com.elhady.movies.ui.base.BaseFragment


class EpisodesFragment : BaseFragment<FragmentEpisodesBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_episodes
    override val viewModel: EpisodesViewModel by viewModels()

}