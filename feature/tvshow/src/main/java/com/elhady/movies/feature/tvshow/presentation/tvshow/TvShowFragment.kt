package com.elhady.movies.feature.tvshow.presentation.tvshow

import android.os.Bundle
import android.view.View
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.elhady.movies.feature.tvshow.BR
import com.elhady.movies.feature.tvshow.R
import com.elhady.movies.core.ui.adapter.BaseFooterAdapter
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.feature.tvshow.databinding.FragmentTvShowsBinding
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.tvshow.presentation.tvshow.adapter.TvShowAdapter
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowFragment : BaseFragment<FragmentTvShowsBinding, TvShowUiState, TvShowUiEvent>() {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment = R.layout.fragment_tv_shows
    override val viewModel: TvShowViewModel by viewModels()
    override val viewModelVariableId: Int = BR.viewModel
    private val tvShowsAdapter by lazy { TvShowAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        doNothingWhenTheSameChipIsReselected()
    }

    private fun setAdapter() {
        val footerAdapter = BaseFooterAdapter { tvShowsAdapter.retry() }
        binding.recyclerViewTvShows.adapter = tvShowsAdapter.withLoadStateFooter(footerAdapter)

        val mManager = binding.recyclerViewTvShows.layoutManager as GridLayoutManager
        mManager.setSpanSize(footerAdapter, tvShowsAdapter, mManager.spanCount)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                val flow = when (state.tvShowType) {
                    TvShowType.AIRING_TODAY -> state.tvShowAiringToday
                    TvShowType.ON_THE_AIR -> state.tvShowOnTheAir
                    TvShowType.TOP_RATED -> state.tvShowTopRated
                    TvShowType.POPULAR -> state.tvShowPopular
                }
                collectLast(flow) { itemsPagingData ->
                    viewLifecycleOwner.lifecycleScope.launch {
                        tvShowsAdapter.submitData(itemsPagingData)
                    }
                }
                collectLast(tvShowsAdapter.loadStateFlow) { viewModel.setErrorUiState(it) }
            }
        }
    }

    override fun onEvent(event: TvShowUiEvent) {
        when (event) {
            is TvShowUiEvent.ShowOnTheAirTvShowsResult -> viewModel.getOnTheAirTvShows()
            is TvShowUiEvent.ShowAiringTodayTvShowsResult -> viewModel.getAiringTodayTvShows()
            is TvShowUiEvent.ShowTopRatedTvShowsResult -> viewModel.getTopRatedTvShows()
            is TvShowUiEvent.ShowPopularTvShowsResult -> viewModel.getPopularTvShows()
            is TvShowUiEvent.NavigateToTvShowDetails -> navigate(event.tvId)
            is TvShowUiEvent.ShowSnackBar -> showSnackBar(event.messages)
            is TvShowUiEvent.ScrollToTopRecycler -> binding.recyclerViewTvShows.scrollToPosition(0)
        }
    }

    private fun navigate(tvId: Int) {
        navigator.navigateToTvDetails(tvId)
    }


    private fun doNothingWhenTheSameChipIsReselected() {
        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedId ->
            if (checkedId.isNotEmpty()) {
                val chip = group.findViewById<Chip>(checkedId.first())
                chip.let {
                    group.forEach { itemChip -> itemChip.isClickable = true }
                    chip.isClickable = false
                }
            }
        }
    }
}
