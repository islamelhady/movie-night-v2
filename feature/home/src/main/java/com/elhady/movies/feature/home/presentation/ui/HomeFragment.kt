package com.elhady.movies.feature.home.presentation.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.elhady.movies.feature.home.BR
import com.elhady.movies.feature.home.R
import com.elhady.movies.feature.home.presentation.ui.adapter.HomeAdapter
import com.elhady.movies.core.common.bases.BaseFragment
import com.elhady.movies.feature.home.databinding.FragmentHomeBinding
import com.elhady.movies.feature.home.presentation.HomeUiEvent
import com.elhady.movies.feature.home.presentation.HomeUiState
import com.elhady.movies.feature.home.presentation.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.net.toUri

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeUiState, HomeUiEvent>() {

    override val layoutIdFragment: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()
    override val viewModelVariableId: Int = BR.viewModel

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
                val request = NavDeepLinkRequest.Builder
                    .fromUri(Uri.parse("movie://movie_details/${event.itemId}"))
                    .build()
                findNavController().navigate(request)
            }

            is HomeUiEvent.TvShowEvent -> {
                val request = NavDeepLinkRequest.Builder
                    .fromUri(Uri.parse("movie://tv_details/${event.itemId}"))
                    .build()
                findNavController().navigate(request)
            }

            is HomeUiEvent.ClickShowMoreEvent -> {
                val request = NavDeepLinkRequest.Builder
                    .fromUri(Uri.parse("movie://show_more/${event.showMore}"))
                    .build()
                findNavController().navigate(request)
            }

            is HomeUiEvent.ShowSnackBarEvent -> {
                showSnackBar(event.message)
            }
        }
    }
}
