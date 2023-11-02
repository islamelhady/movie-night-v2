package com.elhady.movies.ui.seriesDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentSeriesDetailsBinding
import com.elhady.movies.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SeriesDetailsFragment : BaseFragment<FragmentSeriesDetailsBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_series_details
    override val viewModel: SeriesDetailsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val seriesAdapter = SeriesDetailsAdapter(mutableListOf(), viewModel)
        binding.recyclerSeriesDetails.adapter = seriesAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.seriesUiState.collect {
                seriesAdapter.setItems(viewModel.seriesUiState.value.seriesItems)
            }
        }
    }


}