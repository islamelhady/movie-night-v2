package com.elhady.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.elhady.base.BaseFragment
import com.elhady.ui.R
import com.elhady.ui.databinding.FragmentHomeBinding
import com.elhady.ui.home.adapters.HomeAdapter
import com.elhady.viewmodel.home.HomeViewModel
import com.elhady.viewmodel.home.homeUiState.HomeUiEvent
import com.elhady.viewmodel.home.homeUiState.HomeUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeUiState, HomeUiEvent>() {

    override val layoutIdFragment: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(true)
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

            is HomeUiEvent.ClickSeeAllMoviesEvent -> navigateToSeeAllMovies(event.mediaType)

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

            is HomeUiEvent.ClickSeeAllSeriesEvent ->  navigateToSeeAllTvShow(event.mediaType)
        }
    }

    private fun navigateToSeeAllMovies(type: SeeAllType) {
       when(type){
           SeeAllType.UPCOMING_MOVIE -> {
               findNavController().navigate(HomeFragmentDirections
                   .actionHomeFragmentToAllMediaFragment(SeeAllType.UPCOMING_MOVIE,-1))
           }
           SeeAllType.TRENDING_MOVIE -> {
               findNavController().navigate(HomeFragmentDirections
                   .actionHomeFragmentToAllMediaFragment(SeeAllType.TRENDING_MOVIE,-1))
           }
           SeeAllType.NOW_PLAYING_MOVIE -> {
               findNavController().navigate(HomeFragmentDirections
                   .actionHomeFragmentToAllMediaFragment(SeeAllType.NOW_PLAYING_MOVIE,-1))
           }
           SeeAllType.TOP_RATED_MOVIE -> {
               findNavController().navigate(HomeFragmentDirections
                   .actionHomeFragmentToAllMediaFragment(SeeAllType.TOP_RATED_MOVIE,-1))
           }
           SeeAllType.MYSTERY_MOVIE -> {
               findNavController().navigate(HomeFragmentDirections
                   .actionHomeFragmentToAllMediaFragment(SeeAllType.MYSTERY_MOVIE,-1))
           }
           SeeAllType.ADVENTURE_MOVIE -> {
               findNavController().navigate(HomeFragmentDirections
                   .actionHomeFragmentToAllMediaFragment(SeeAllType.ADVENTURE_MOVIE,-1))
           }

           else -> {
               TODO()
           }
       }

    }

    private fun navigateToSeeAllTvShow(type: SeeAllType){
        when(type){
            SeeAllType.TOP_RATED_TV -> {
                findNavController().navigate(HomeFragmentDirections
                    .actionHomeFragmentToAllMediaFragment(SeeAllType.TOP_RATED_TV,-1))
            }
            SeeAllType.POPULAR_TV -> {
                findNavController().navigate(HomeFragmentDirections
                    .actionHomeFragmentToAllMediaFragment(SeeAllType.POPULAR_TV,-1))
            }
            SeeAllType.LATEST_TV -> {
                findNavController().navigate(HomeFragmentDirections
                    .actionHomeFragmentToAllMediaFragment(SeeAllType.LATEST_TV,-1))
            }
            SeeAllType.ON_THE_AIR_TV -> {
                findNavController().navigate(HomeFragmentDirections
                    .actionHomeFragmentToAllMediaFragment(SeeAllType.ON_THE_AIR_TV,-1))
            }
            else -> {}
        }
    }


}