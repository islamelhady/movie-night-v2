package com.elhady.movies.presentation.ui.tv_shows

import android.os.Bundle
import android.view.View
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseFooterAdapter
import com.elhady.movies.core.bases.BaseFragment
import com.elhady.movies.databinding.FragmentTvShowsBinding
import com.elhady.movies.presentation.viewmodel.tv_shows.TVShowUIState
import com.elhady.movies.presentation.viewmodel.tv_shows.TVShowsUiEvent
import com.elhady.movies.presentation.viewmodel.tv_shows.TVShowsType
import com.elhady.movies.presentation.viewmodel.tv_shows.TVShowsViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowsFragment : BaseFragment<FragmentTvShowsBinding, TVShowUIState, TVShowsUiEvent>() {

    override val layoutIdFragment = R.layout.fragment_tv_shows
    override val viewModel: TVShowsViewModel by viewModels()
    private val tvShowsAdapter by lazy { TVShowsAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        doNothingWhenTheSameChipIsReselected()
    }

    private fun setAdapter() {
        val footerAdapter = BaseFooterAdapter { tvShowsAdapter.retry() }
        binding.recyclerViewTvShows.adapter = tvShowsAdapter.withLoadStateFooter(footerAdapter)

        val mManager = binding.recyclerViewTvShows.layoutManager as GridLayoutManager
        mManager.setSpanSize(footerAdapter, tvShowsAdapter, mManager.spanCount)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                val flow = when (state.tvShowsType) {
                    TVShowsType.AIRING_TODAY -> state.tvShowAiringToday
                    TVShowsType.ON_THE_AIR -> state.tvShowOnTheAir
                    TVShowsType.TOP_RATED -> state.tvShowTopRated
                    TVShowsType.POPULAR -> state.tvShowPopular
                }
                collectLast(flow) { itemsPagingData ->
                    viewLifecycleOwner.lifecycleScope.launch {
                        tvShowsAdapter.submitData(itemsPagingData)
                    }
                }
                collectLast(tvShowsAdapter.loadStateFlow) { viewModel.setErrorUiState(it) }
            }
        }
    }

    override fun onEvent(event: TVShowsUiEvent) {
        when (event) {
            is TVShowsUiEvent.ShowOnTheAirTVShowsResult -> viewModel.getOnTheAirTVShows()
            is TVShowsUiEvent.ShowAiringTodayTVShowsResult -> viewModel.getAiringTodayTVShows()
            is TVShowsUiEvent.ShowTopRatedTVShowsResult -> viewModel.getTopRatedTVShows()
            is TVShowsUiEvent.ShowPopularTVShowsResult -> viewModel.getPopularTVShows()
            is TVShowsUiEvent.NavigateToTVShowDetails -> findNavController().navigate(
                TvShowsFragmentDirections.actionTvFragmentToTvDetailsFragment(event.tvId)
            )

            is TVShowsUiEvent.ShowSnackBar -> showSnackBar(event.messages)
            is TVShowsUiEvent.ScrollToTopRecycler -> binding.recyclerViewTvShows.scrollToPosition(0)
        }
    }


    private fun doNothingWhenTheSameChipIsReselected() {
        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId.first())
            chip.let {
                group.forEach { itemChip -> itemChip.isClickable = true }
                chip.isClickable = false
            }
        }
    }
}