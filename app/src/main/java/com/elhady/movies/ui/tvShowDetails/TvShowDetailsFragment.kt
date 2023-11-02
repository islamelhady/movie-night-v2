package com.elhady.movies.ui.tvShowDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentTvShowDetailsBinding
import com.elhady.movies.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowDetailsFragment : BaseFragment<FragmentTvShowDetailsBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_tv_show_details
    override val viewModel: TvShowDetailsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvShowDetailsAdapter = TvShowDetailsAdapter(mutableListOf(), viewModel)
        binding.recyclerTvShowDetails.adapter = tvShowDetailsAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.seriesUiState.collect {
                tvShowDetailsAdapter.setItems(viewModel.seriesUiState.value.seriesItems)
            }
        }
    }


}