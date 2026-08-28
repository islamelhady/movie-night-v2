package com.elhady.movies.feature.tvshow.presentation.tvshow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.elhady.movies.core.ui.adapter.BaseFooterAdapter
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.interaction.TvShowAdapterListener
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.tvshow.R
import com.elhady.movies.feature.tvshow.databinding.FragmentTvShowsBinding
import com.elhady.movies.feature.tvshow.presentation.tvshow.adapter.TvShowAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TvShowFragment : BaseFragment<FragmentTvShowsBinding, TvShowUiState, TvShowUiEffect>(),
    TvShowFragmentListener {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment = R.layout.fragment_tv_shows
    override val viewModel: TvShowViewModel by viewModels()
    private val tvShowAdapter by lazy {
        TvShowAdapter(
            listener = object : TvShowAdapterListener {
                override fun onClickTvShow(id: Int) {
                    viewModel.onEvent(TvShowUiEvent.TvShowItemClicked(id))
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listener = this
        setupRecyclerView()
        collectPagingData()
    }

    private fun setupRecyclerView() {
        val footerAdapter = BaseFooterAdapter { tvShowAdapter.retry() }
        binding.recyclerViewTvShows.adapter = tvShowAdapter.withLoadStateFooter(footerAdapter)

        val layoutManager = binding.recyclerViewTvShows.layoutManager as GridLayoutManager
        layoutManager.setSpanSize(
            footerAdapter,
            tvShowAdapter,
            layoutManager.spanCount
        )

        collectFlow(tvShowAdapter.loadStateFlow) { loadStates ->
            viewModel.onPagingLoadStateChanged(loadStates)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun collectPagingData() {
        val pagingFlow = viewModel.state
            .map { state ->
                when (state.tvShowType) {
                    TvShowType.AIRING_TODAY -> state.tvShowAiringToday
                    TvShowType.ON_THE_AIR -> state.tvShowOnTheAir
                    TvShowType.TOP_RATED -> state.tvShowTopRated
                    TvShowType.POPULAR -> state.tvShowPopular
                }
            }
            .distinctUntilChanged()
            .flatMapLatest { it }

        collectFlow(pagingFlow) { pagingData ->
            tvShowAdapter.submitData(pagingData)
        }
    }

    override fun onEffect(effect: TvShowUiEffect) {
        when (effect) {
            is TvShowUiEffect.NavigateToTvShowDetails -> {
                navigator.navigateToTvDetails(effect.tvId)
            }

            TvShowUiEffect.ScrollToTop -> {
                binding.recyclerViewTvShows.scrollToPosition(0)
            }

            is TvShowUiEffect.ShowSnackBar -> {
                showSnackBar(effect.messages)
            }
        }
    }

    override fun render(state: TvShowUiState) {
        binding.state = state
    }

    override fun onAiringTodayClicked() {
        viewModel.onEvent(TvShowUiEvent.AiringTodayTvShowClicked)
    }

    override fun onOnTheAirClicked() {
        viewModel.onEvent(TvShowUiEvent.OnTheAirTvShowClicked)
    }

    override fun onPopularClicked() {
        viewModel.onEvent(TvShowUiEvent.PopularTvShowClicked)
    }

    override fun onTopRatedClicked() {
        viewModel.onEvent(TvShowUiEvent.TopRatedTvShowClicked)
    }

    override fun onRetryClicked() {
        viewModel.onEvent(TvShowUiEvent.RetryClicked)
    }

    override fun onScrollToTopClicked() {
        viewModel.onEvent(TvShowUiEvent.ToTopClicked)
    }
}
