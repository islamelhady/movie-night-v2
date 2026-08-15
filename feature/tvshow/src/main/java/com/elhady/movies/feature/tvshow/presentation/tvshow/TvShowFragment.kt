package com.elhady.movies.feature.tvshow.presentation.tvshow

import android.os.Bundle
import android.view.View
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.elhady.movies.feature.tvshow.BR
import com.elhady.movies.feature.tvshow.R
import com.elhady.movies.core.ui.adapter.BaseFooterAdapter
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.base.animationRes
import com.elhady.movies.feature.tvshow.databinding.FragmentTvShowsBinding
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.tvshow.presentation.tvshow.adapter.TvShowAdapter
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowFragment : BaseFragment<FragmentTvShowsBinding, TvShowUiState, TvShowUiEffect>() {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment = R.layout.fragment_tv_shows
    override val viewModel: TvShowViewModel by viewModels()
    override val viewModelVariableId: Int = BR.viewModel
    private val tvShowAdapter by lazy {
        TvShowAdapter(
            listener = object : TvShowListener {
                override fun onClickMedia(id: Int) {
                    viewModel.onEvent(TvShowUiEvent.TvShowItemClicked(id))
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListener()

        collectFlow(viewModel.state) { state ->
            render(state)
        }
        doNothingWhenTheSameChipIsReselected()
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

        viewLifecycleOwner.lifecycleScope.launch {
            tvShowAdapter.loadStateFlow.collectLatest { loadStates ->
                viewModel.onPagingLoadStateChanged(loadStates)
            }
        }
    }

    private fun setupListener() {
        binding.chipAiringToday.setOnClickListener {
            viewModel.onEvent(TvShowUiEvent.AiringTodayTvShowClicked)
        }
        binding.chipOnTheAir.setOnClickListener {
            viewModel.onEvent(TvShowUiEvent.OnTheAirTvShowClicked)
        }
        binding.chipPopular.setOnClickListener {
            viewModel.onEvent(TvShowUiEvent.PopularTvShowClicked)
        }
        binding.chipTopRated.setOnClickListener {
            viewModel.onEvent(TvShowUiEvent.TopRatedTvShowClicked)
        }
        binding.fabScrollToTop.setOnClickListener {
            viewModel.onEvent(TvShowUiEvent.ToTopClicked)
        }
        binding.buttonRetry.setOnClickListener {
            viewModel.onEvent(TvShowUiEvent.RetryClicked)
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

    private fun render(state: TvShowUiState) {
        renderLoading(state)
        renderError(state)
        collectPagingState(state)
        renderChipGroup(state)
    }


    private fun renderLoading(state: TvShowUiState) {
        binding.loadingAnimation.isVisible = state.isLoading
    }

    private fun renderError(state: TvShowUiState) {
        val error = state.error
        binding.errorAnimation.isVisible = error != null
        binding.buttonRetry.isVisible = error != null

        if (error == null) {
            binding.errorAnimation.cancelAnimation()
            return
        }

        binding.errorAnimation.setAnimation(error.animationRes)
        binding.errorAnimation.playAnimation()
    }

    private fun collectPagingState(state: TvShowUiState) {
        val tvShowPaging = when (state.tvShowType) {
            TvShowType.AIRING_TODAY -> state.tvShowAiringToday
            TvShowType.ON_THE_AIR -> state.tvShowOnTheAir
            TvShowType.TOP_RATED -> state.tvShowTopRated
            TvShowType.POPULAR -> state.tvShowPopular
        }
        binding.recyclerViewTvShows.isVisible = !state.isLoading && state.error == null

        viewLifecycleOwner.lifecycleScope.launch {
            tvShowPaging.collectLatest { pagingData ->
                tvShowAdapter.submitData(pagingData)
            }
        }

    }

    private fun renderChipGroup(state: TvShowUiState) {
        when (state.tvShowType) {
            TvShowType.AIRING_TODAY -> binding.chipAiringToday.isChecked = true
            TvShowType.ON_THE_AIR -> binding.chipOnTheAir.isChecked = true
            TvShowType.TOP_RATED -> binding.chipTopRated.isChecked = true
            TvShowType.POPULAR -> binding.chipPopular.isChecked = true
        }
    }

    private fun doNothingWhenTheSameChipIsReselected() {

        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->

            val chip = group.findViewById<Chip>(
                checkedIds.first()
            )

            group.forEach { itemChip ->
                itemChip.isClickable = true
            }

            chip.isClickable = false
        }
    }
}
