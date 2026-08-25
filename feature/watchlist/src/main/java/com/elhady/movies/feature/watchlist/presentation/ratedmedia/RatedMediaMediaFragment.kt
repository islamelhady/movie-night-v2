package com.elhady.movies.feature.watchlist.presentation.ratedmedia

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.interaction.MovieAdapterListener
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.feature.watchlist.databinding.FragmentMyRatedBinding
import com.elhady.movies.feature.watchlist.presentation.ratedmedia.adapter.RatedMediaAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RatedMediaMediaFragment : BaseFragment<FragmentMyRatedBinding, MyRatedUiState, RatedMediaUiEffect>(),
    RatedMediaListener, MovieAdapterListener {

    override val layoutIdFragment: Int =
        R.layout.fragment_my_rated

    override val viewModel: RatedMediaViewModel by viewModels()

    @Inject
    lateinit var navigator: Navigator

    private val movieAdapter: RatedMediaAdapter by lazy {
        RatedMediaAdapter(
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

    override fun onEffect(effect: RatedMediaUiEffect) {
        when (effect) {

            RatedMediaUiEffect.NavigateBack -> {
                navigator.navigateBack()
            }

            is RatedMediaUiEffect.NavigateToMovieDetails -> {
                navigator.navigateToMovieDetails(
                    effect.movieId
                )
            }

            is RatedMediaUiEffect.NavigateToTvShowDetails -> {
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
            RatedMediaUiEvent.BackClicked
        )
    }

    override fun onClickMovieChip() {
        viewModel.onEvent(
            RatedMediaUiEvent.MoviesSelected
        )

    }

    override fun onClickTvShowChip() {
        viewModel.onEvent(
            RatedMediaUiEvent.TvShowsSelected
        )
    }

    override fun onClickMovie(id: Int) {
        viewModel.onEvent(
            RatedMediaUiEvent.MediaClicked(id)
        )
    }
}
