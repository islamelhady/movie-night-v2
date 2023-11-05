package com.elhady.movies.ui.explore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentExploreBinding
import com.elhady.movies.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding>() {

    override val layoutIdFragment: Int = R.layout.fragment_explore
    override val viewModel: ExploreViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerExplore.adapter = TrendingAdapter(mutableListOf(), viewModel)
    }
}