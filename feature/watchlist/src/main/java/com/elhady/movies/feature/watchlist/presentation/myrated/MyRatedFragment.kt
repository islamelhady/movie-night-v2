package com.elhady.movies.feature.watchlist.presentation.myrated

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.interaction.MovieAdapterListener
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.feature.watchlist.databinding.FragmentMyRatedBinding
import com.elhady.movies.feature.watchlist.presentation.myrated.adapter.MyRateAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyRatedFragment : BaseFragment<FragmentMyRatedBinding, MyRatedUiState, MyRatedUiEffect>(),
    MyRatedListener, MovieAdapterListener {

    override val layoutIdFragment: Int =
        R.layout.fragment_my_rated

    override val viewModel: MyRatedViewModel by viewModels()

    @Inject
    lateinit var navigator: Navigator

    private val movieAdapter: MyRateAdapter by lazy {
        MyRateAdapter(
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
        binding.listener = this
    }

    override fun onEffect(effect: MyRatedUiEffect) {
        when (effect) {

            MyRatedUiEffect.NavigateBack -> {
                navigator.navigateBack()
            }

            is MyRatedUiEffect.NavigateToMovieDetails -> {
                navigator.navigateToMovieDetails(
                    effect.movieId
                )
            }

            is MyRatedUiEffect.NavigateToTvShowDetails -> {
                navigator.navigateToTvDetails(
                    effect.tvShowId
                )
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewMedia.adapter = movieAdapter
    }

    private fun collectState() {
        collectFlow(viewModel.state) { state ->
            render(state)

        }
    }

    private fun render(state: MyRatedUiState) {
        binding.state = state
        collectFlow(flow = state.movies) { itemsPagingData ->
            movieAdapter.submitData(itemsPagingData)
        }
        collectFlow(movieAdapter.loadStateFlow) { loadState ->
            viewModel.setErrorUiState(loadState)
        }
    }


    override fun onBackPressed() {
        viewModel.onEvent(
            MyRatedUiEvent.BackClicked
        )
    }

    override fun onClickMovieChip() {
        viewModel.onEvent(
            MyRatedUiEvent.MoviesSelected
        )

    }

    override fun onClickTvShowChip() {
        viewModel.onEvent(
            MyRatedUiEvent.TvShowsSelected
        )
    }

    override fun onClickMovie(id: Int) {
        viewModel.onEvent(
            MyRatedUiEvent.MediaClicked(id)
        )
    }
}
