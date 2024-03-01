package com.elhady.movies.ui.explore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentExploreBinding
import com.elhady.movies.domain.enums.MediaType
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.ui.explore.TrendingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding, ExploreUiState, ExploreUiEvent>() {

    override val layoutIdFragment: Int = R.layout.fragment_explore
    override val viewModel: ExploreViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(true)
        binding.recyclerExplore.adapter = TrendingAdapter(mutableListOf(), viewModel)
    }

    override fun onEvent(event: ExploreUiEvent) {
        when (event) {
            is ExploreUiEvent.ClickTrendEvent -> {
                navigateToMediaDetails(event.trendingMediaUiState)
            }

            ExploreUiEvent.ScrollToTopRecycler -> binding.recyclerExplore.scrollToPosition(0)
            ExploreUiEvent.ClickActorsEvent -> findNavController().navigate(
                ExploreFragmentDirections.actionExploreFragmentToActorsFragment()
            )

            ExploreUiEvent.ClickMoviesEvent -> findNavController().navigate(
                ExploreFragmentDirections.actionExploreFragmentToCategoryFragment(MediaType.MOVIES)
            )

            ExploreUiEvent.ClickSeriesEvent -> findNavController().navigate(
                ExploreFragmentDirections.actionExploreFragmentToCategoryFragment(MediaType.SERIES)
            )

            ExploreUiEvent.ClickSearchEvent -> {
                val extras = FragmentNavigatorExtras(binding.inputTextSearch to "search_box")
                findNavController().navigate(
                    ExploreFragmentDirections.actionExploreFragmentToSearchFragment(),
                    extras
                )
            }
        }
    }

    private fun navigateToMediaDetails(media: TrendingMediaUiState) {
        when (media.type) {
            MediaType.SERIES.value -> {
                findNavController().navigate(
                    ExploreFragmentDirections.actionExploreFragmentToTvShowDetailsFragment(
                        media.id
                    )
                )
            }
            MediaType.MOVIES.value -> {
                findNavController().navigate(
                    ExploreFragmentDirections.actionExploreFragmentToMovieDetailsFragment(
                        media.id
                    )
                )
            }
        }
    }
}