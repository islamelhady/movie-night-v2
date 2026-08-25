package com.elhady.movies.feature.home.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.core.common.ShowMoreType
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.home.R
import com.elhady.movies.feature.home.presentation.home.adapter.HomeAdapter
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.feature.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeUiState, HomeUiEffect>(), HomeAdapterListener {
    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()

    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(
            items = mutableListOf(),
            listener = this
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        collectState()
    }


    private fun setupRecyclerView() {
        binding.recyclerViewHome.adapter = homeAdapter
    }

    private fun collectState() {
        collectFlow(viewModel.state) { state ->
            render(state)
        }
    }

    private fun render(state: HomeUiState) {
        binding.state = state

        homeAdapter.setItems(
            listOf(
                HomeItem.Slider(state.upcomingMovies),
                HomeItem.NowPlaying(state.nowPlayingMovies),
                HomeItem.TvShow(state.tvShows),
                HomeItem.AiringTodayTvShow(state.airingTodayTvShows),
                HomeItem.TrendingMovie(state.trendingMovies),
                HomeItem.TopRatedMovie(state.topRatedMovies),
                HomeItem.PopularPeople(state.popularPeople),
                HomeItem.PopularMovies(state.popularMovies)
            )
        )
    }

    override fun onEffect(effect: HomeUiEffect) {
        when (effect) {

            is HomeUiEffect.NavigateToMovieDetails -> {
                navigator.navigateToMovieDetails(effect.movieId)
            }

            is HomeUiEffect.NavigateToTvShowDetails -> {
                navigator.navigateToTvDetails(effect.tvShowId)
            }

            is HomeUiEffect.NavigateToShowMore -> {
                navigator.navigateToShowMore(effect.type)
            }

            is HomeUiEffect.ShowSnackBar -> {
                showSnackBar(effect.message)
            }

            is HomeUiEffect.NavigateToPeopleDetails -> {
                navigator.navigateToPeopleDetails(effect.personId)
            }
        }
    }

    override fun onMovieClick(id: Int) {
        viewModel.onEvent(HomeUiEvent.MovieClicked(id))
    }

    override fun onTvShowClick(id: Int) {
        viewModel.onEvent(HomeUiEvent.TvShowClicked(id))
    }

    override fun onClickPeople(id: Int) {
        viewModel.onEvent(HomeUiEvent.PeopleClicked(id))
    }

    override fun onClickShowMore(type: ShowMoreType) {
        viewModel.onEvent(HomeUiEvent.ShowMoreClicked(type))
    }

    override fun onClickRetry() {
        viewModel.onEvent(HomeUiEvent.RetryClicked)
    }

}
