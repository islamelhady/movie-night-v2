package com.elhady.movies.ui.tvShowDetails

import androidx.fragment.app.viewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentTvShowDetailsBinding
import com.elhady.movies.ui.base.BaseFragment

class TvShowDetailsFragment : BaseFragment<FragmentTvShowDetailsBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_tv_show_details
    override val viewModel: TvShowDetailsViewModel by viewModels()


}