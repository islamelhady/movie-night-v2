package com.elhady.movies.feature.showmore.presentation.showmore

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.elhady.movies.core.common.ShowMoreType
import com.elhady.movies.core.domain.model.account.ListType
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.showmore.R
import com.elhady.movies.feature.showmore.databinding.FragmentShowMoreBinding
import com.elhady.movies.feature.showmore.presentation.showmore.adapter.ShowMoreAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint
class ShowMoreFragment : BaseFragment<FragmentShowMoreBinding, ShowMoreUiState, ShowMoreUiEffect>(),
    ShowMoreAdapterListener, ShowMoreFragmentListener {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int = R.layout.fragment_show_more
    override val viewModel: ShowMoreViewModel by viewModels()

    private val showMoreAdapter by lazy { ShowMoreAdapter(listener = this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listener = this
        setAdapter()
        collectPagingData()
        collectLoadStates()
    }

    private fun setAdapter() {
        binding.recyclerMedia.adapter = showMoreAdapter
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun collectPagingData() {
        val pagingFlow = viewModel.state
            .map { state ->
                when (state.showMoreType) {
                    ShowMoreType.POPULAR_MOVIES -> state.showMorePopularMovies
                    ShowMoreType.TOP_RATED_MOVIES -> state.showMoreTopRatedMovies
                    ShowMoreType.TRENDING_MOVIES -> state.showMoreTrendingMovies
                    ShowMoreType.AIRING_TODAY_TV -> state.showMoreAiringTodayTvShow
                    ShowMoreType.TOP_RATED_TV -> state.showMoreTopRatedTvShow
                    ShowMoreType.POPULAR_TV -> state.showMorePopularTvShow
                    ShowMoreType.ON_THE_AIR_TV -> state.showMoreOnTheAirTvShow
                }
            }
            .distinctUntilChanged()
            .flatMapLatest { it }

        collectFlow(pagingFlow) { itemsPagingData ->
            showMoreAdapter.submitData(itemsPagingData)
        }
    }

    private fun collectLoadStates() {
        collectFlow(showMoreAdapter.loadStateFlow) { loadStates ->
            viewModel.setErrorUiState(loadStates)
            binding.lottieAnimationEmpty.isVisible =
                loadStates.refresh is LoadState.NotLoading && showMoreAdapter.itemCount == 0
        }
    }

    override fun render(state: ShowMoreUiState) {
        binding.state = state
    }

    override fun onEffect(effect: ShowMoreUiEffect) {
        when (effect) {
            is ShowMoreUiEffect.NavigateToMovieDetails -> navigator.navigateToMovieDetails(effect.id)
            is ShowMoreUiEffect.NavigateToTvShowDetails -> navigator.navigateToTvDetails(effect.id)
            ShowMoreUiEffect.NavigateBack -> navigator.navigateBack()
            is ShowMoreUiEffect.ShowSnackBar -> showSnackBar(effect.message)
        }
    }

    override fun onClickItem(mediaId: Int, type: ListType) {
        viewModel.onEvent(ShowMoreUiEvent.ItemClicked(mediaId, type))
    }

    override fun onClickBack() {
        viewModel.onEvent(ShowMoreUiEvent.BackClicked)
    }

    override fun onClickRetry() {
        viewModel.onEvent(ShowMoreUiEvent.RetryClicked)
    }

}
