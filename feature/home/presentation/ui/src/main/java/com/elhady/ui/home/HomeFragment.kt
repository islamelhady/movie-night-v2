package com.elhady.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elhady.base.BaseFragment
import com.elhady.ui.R
import com.elhady.ui.databinding.FragmentHomeBinding
import com.elhady.ui.home.adapters.HomeAdapter
import com.elhady.viewmodel.home.HomeViewModel
import com.elhady.viewmodel.showMore.ShowMoreType
import com.elhady.viewmodel.home.homeUiState.HomeUiEvent
import com.elhady.viewmodel.home.homeUiState.HomeUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeUiState, HomeUiEvent>() {

    override val layoutIdFragment: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        setTitle(true)
        setAdapter()
        collectHomeDataChanges()
    }

    private fun setAdapter() {
        homeAdapter = HomeAdapter(mutableListOf(), viewModel)
        binding.recyclerView.adapter = homeAdapter
    }

    private fun collectHomeDataChanges() {
        collectLatest {
            viewModel.state.collect { state ->
                homeAdapter.setItems(
                    mutableListOf(
                        HomeItem.PopularMovieSlider(state.popularMovieSlider),
                        HomeItem.UpcomingMovies(state.upcomingMovie),
                        HomeItem.TrendingMovies(state.trendingMovie),
                        HomeItem.NowPlayingMovies(state.nowPlayingMovie),
                        HomeItem.TopRatedMovies(state.topRatedMovie),
                        HomeItem.OnTheAirTvShows(state.onTheAirTVShows),
                        HomeItem.AiringTodayTvShows(state.airingTodayTVShows),
                        HomeItem.TvShowsLists(state.tvShowsLists),
                        HomeItem.MysteryMovies(state.mysteryMovies),
                        HomeItem.AdventureMovies(state.adventureMovies),
                        HomeItem.PopularActor(state.popularActor)
                    )
                )
                binding.recyclerView.smoothScrollToPosition(0)
            }
        }
    }

    override fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.ClickMovieEvent -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(
                        event.id
                    )
                )
            }

            is HomeUiEvent.ClickShowMoreEvent -> navigateToShowMoreMovies(event.mediaType)

            is HomeUiEvent.ClickShowMoreEvent -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToActorsFragment()
                )
            }

            is HomeUiEvent.ClickActorEvent -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToActorDetailsFragment(event.id)
                )
            }

            is HomeUiEvent.ClickTVShowEvent -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToTvShowDetailsFragment(event.id)
                )
            }

            is HomeUiEvent.ClickShowMoreEvent ->  navigateToShowMoreTvShow(event.mediaType)
        }
    }

    private fun navigateToShowMoreMovies(type: ShowMoreType) {
       when(type){
           ShowMoreType.UPCOMING_MOVIES -> {
               findNavController().navigate(HomeFragmentDirections
                   .actionHomeFragmentToAllMediaFragment(ShowMoreType.UPCOMING_MOVIES,-1))
           }
           ShowMoreType.TRENDING_MOVIES -> {
               findNavController().navigate(HomeFragmentDirections
                   .actionHomeFragmentToAllMediaFragment(ShowMoreType.TRENDING_MOVIES,-1))
           }
           ShowMoreType.NOW_PLAYING_MOVIES -> {
               findNavController().navigate(HomeFragmentDirections
                   .actionHomeFragmentToAllMediaFragment(ShowMoreType.NOW_PLAYING_MOVIES,-1))
           }
           ShowMoreType.TOP_RATED_MOVIES -> {
               findNavController().navigate(HomeFragmentDirections
                   .actionHomeFragmentToAllMediaFragment(ShowMoreType.TOP_RATED_MOVIES,-1))
           }
           ShowMoreType.MYSTERY_MOVIES -> {
               findNavController().navigate(HomeFragmentDirections
                   .actionHomeFragmentToAllMediaFragment(ShowMoreType.MYSTERY_MOVIES,-1))
           }
           ShowMoreType.ADVENTURE_MOVIES -> {
               findNavController().navigate(HomeFragmentDirections
                   .actionHomeFragmentToAllMediaFragment(ShowMoreType.ADVENTURE_MOVIES,-1))
           }

           else -> {
               TODO()
           }
       }

    }

    private fun navigateToShowMoreTvShow(type: ShowMoreType){
        when(type){
            ShowMoreType.TOP_RATED_TV_SHOW -> {
                findNavController().navigate(HomeFragmentDirections
                    .actionHomeFragmentToAllMediaFragment(ShowMoreType.TOP_RATED_TV_SHOW,-1))
            }
            ShowMoreType.POPULAR_TV_SHOW -> {
                findNavController().navigate(HomeFragmentDirections
                    .actionHomeFragmentToAllMediaFragment(ShowMoreType.POPULAR_TV_SHOW,-1))
            }
            ShowMoreType.LATEST_TV_SHOW -> {
                findNavController().navigate(HomeFragmentDirections
                    .actionHomeFragmentToAllMediaFragment(ShowMoreType.LATEST_TV_SHOW,-1))
            }
            ShowMoreType.ON_THE_AIR_TV_SHOW -> {
                findNavController().navigate(HomeFragmentDirections
                    .actionHomeFragmentToAllMediaFragment(ShowMoreType.ON_THE_AIR_TV_SHOW,-1))
            }
            else -> {}
        }
    }


}