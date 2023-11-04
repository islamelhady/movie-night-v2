package com.elhady.movies.ui.search

import androidx.fragment.app.viewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentExploreBinding
import com.elhady.movies.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding>() {

    override val layoutIdFragment: Int = R.layout.fragment_explore
    override val viewModel: ExploreViewModel by viewModels()
}