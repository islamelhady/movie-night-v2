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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint
class RatedMediaMediaFragment : BaseFragment<FragmentMyRatedBinding, RatedMediaUiState, RatedMediaUiEffect>(),
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
        collectPagingData()
        collectLoadStates()
        binding.listener = this
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun collectPagingData() {
        val pagingFlow = viewModel.state
            .map { it.movies }
            .distinctUntilChanged()
            .flatMapLatest { it }

        collectFlow(pagingFlow) { itemsPagingData ->
            movieAdapter.submitData(itemsPagingData)
        }
    }

    private fun collectLoadStates() {
        collectFlow(movieAdapter.loadStateFlow) { loadState ->
            viewModel.setErrorUiState(loadState)
        }
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

    override fun render(state: RatedMediaUiState) {
        binding.state = state
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
