package com.elhady.movies.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentHomeBinding
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.ui.home.adapters.HomeAdapter
import com.elhady.movies.ui.home.homeUiState.HomeUiEvent
import com.elhady.movies.ui.home.homeUiState.HomeUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeUiState, HomeUiEvent>() {

    override val layoutIdFragment: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        collectHomeData()
    }

    private fun setupAdapter() {
        homeAdapter = HomeAdapter(mutableListOf(), viewModel)
        binding.recyclerView.adapter = homeAdapter
    }

    private fun collectHomeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { items ->
                homeAdapter.setItems(
                    mutableListOf(
                        HomeItem.PopularMovieSlider(items.popularMovieSlider),
                        HomeItem.Upcoming(items.upcomingMovie),
                        HomeItem.Trending(items.trendingMovie),
                        HomeItem.NowPlaying(items.nowPlayingMovie),
                        HomeItem.TopRated(items.topRatedMovie),
                        HomeItem.OnTheAirSeries(items.onTheAirSeries),
                        HomeItem.AiringTodaySeries(items.airingTodaySeries),
                        HomeItem.TVSeriesLists(items.tvSeriesLists),
                        HomeItem.Mystery(items.mysteryMovies),
                        HomeItem.Adventure(items.adventureMovies),
                        HomeItem.Actor(items.popularPeople)
                    )
                )
            }
        }
    }

    override fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.ClickMovieEvent -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(
                        event.movieID
                    )
                )
            }

            is HomeUiEvent.ClickSeeAllMoviesEvent -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToAllMediaFragment(event.mediaType, -1)
                )
            }

            is HomeUiEvent.ClickSeeAllActorsEvent -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToActorsFragment()
                )
            }

            is HomeUiEvent.ClickActorEvent -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToActorDetailsFragment(event.actorID)
                )
            }

            is HomeUiEvent.ClickSeriesEvent -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToTvShowDetailsFragment(event.mediaID)
                )
            }

            is HomeUiEvent.ClickSeeAllSeriesEvent -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToAllMediaFragment(event.mediaType, -1)
                )
            }
        }
    }


}