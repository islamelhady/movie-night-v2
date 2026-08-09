package com.elhady.movies.feature.home.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.home.BR
import com.elhady.movies.feature.home.R
import com.elhady.movies.feature.home.presentation.home.adapter.HomeAdapter
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.feature.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeUiState, HomeUiEvent>() {

    @Inject
    lateinit var navigator: Navigator

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
        collectFlow(flow = viewModel.state) { state ->
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


    override fun onEffect(effect: HomeUiEvent) {
        when (effect) {
            is HomeUiEvent.MovieEvent -> {
                navigator.navigateToMovieDetails(effect.itemId)
            }

            is HomeUiEvent.TvShowEvent -> {
                navigator.navigateToTvDetails(effect.itemId)
            }

            is HomeUiEvent.ClickShowMoreEvent -> {
                navigator.navigateToShowMore(effect.showMore)
            }

            is HomeUiEvent.ShowSnackBarEvent -> {
                showSnackBar(effect.message)
            }
        }
    }
}
