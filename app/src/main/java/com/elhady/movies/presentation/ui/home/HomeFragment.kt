package com.elhady.movies.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elhady.movies.R
import com.elhady.movies.presentation.ui.home.adapter.HomeAdapter
import com.elhady.movies.core.bases.BaseFragment
import com.elhady.movies.databinding.FragmentHomeBinding
import com.elhady.movies.presentation.viewmodel.home.HomeUiEvent
import com.elhady.movies.presentation.viewmodel.home.HomeUiState
import com.elhady.movies.presentation.viewmodel.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeUiState, HomeUiEvent>() {

    override val layoutIdFragment: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()

    private lateinit var homeAdapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        collectChange()
    }

    private fun setAdapter() {
        homeAdapter = HomeAdapter(mutableListOf(), viewModel)
        binding.recyclerViewHome.adapter = homeAdapter
    }

    private fun collectChange() {
        collectLatest {
            viewModel.state.collect { state ->
                homeAdapter.setItems(
                    mutableListOf(
                        HomeItem.Slider(state.upComingMovies),
                        HomeItem.NowPlaying(state.nowPlayingMovies),
                        HomeItem.Trending(state.trendingMovies),
                        HomeItem.TopRated(state.topRated),
                        HomeItem.PopularPeople(state.popularPeople),
                        HomeItem.TvShow(state.tvShow),
                        HomeItem.PopularMovies(state.popularMovies),
                        HomeItem.AiringTodayTvShow(state.airingTodayTvShow)
                    )
                )
                binding.recyclerViewHome.smoothScrollToPosition(0)
            }
        }
    }

    override fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.MovieEvent -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(
                        movieId = event.itemId
                    )
                )
            }

            is HomeUiEvent.TvShowEvent -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToTvDetailsFragment(
                        tvShowId = event.itemId
                    )
                )
            }

            is HomeUiEvent.ClickShowMoreEvent -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToShowMoreFragment(
                        showMoreType = event.showMore
                    )
                )
            }

            is HomeUiEvent.ShowSnackBarEvent -> {
                showSnackBar(event.message)
            }
        }
    }
}