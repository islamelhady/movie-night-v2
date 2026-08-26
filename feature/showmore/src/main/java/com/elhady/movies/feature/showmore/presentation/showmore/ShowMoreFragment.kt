package com.elhady.movies.feature.showmore.presentation.showmore

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.elhady.movies.core.common.ShowMoreType
import com.elhady.movies.core.domain.model.account.ListType
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.base.animationRes
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.showmore.R
import com.elhady.movies.feature.showmore.databinding.FragmentShowMoreBinding
import com.elhady.movies.feature.showmore.presentation.showmore.adapter.ShowMoreAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShowMoreFragment : BaseFragment<FragmentShowMoreBinding, ShowMoreUiState, ShowMoreUiEffect>() {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int = R.layout.fragment_show_more
    override val viewModel: ShowMoreViewModel by viewModels()

    private val listener = object : ShowMoreListener {
        override fun onClickItem(mediaId: Int, type: ListType) {
            viewModel.onEvent(ShowMoreUiEvent.ItemClicked(mediaId, type))
        }
        override fun onClickBackNavigate() {
            viewModel.onEvent(ShowMoreUiEvent.BackClicked)
        }
    }
    private val showMoreAdapter by lazy { ShowMoreAdapter(listener = listener) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setListeners()
    }

    private fun setAdapter() {
        binding.recyclerMedia.adapter = showMoreAdapter
    }

    private fun setListeners() {
        binding.toolbar.setNavigationOnClickListener {
            viewModel.onEvent(ShowMoreUiEvent.BackClicked)
        }
        binding.buttonRetry.setOnClickListener {
            viewModel.onEvent(ShowMoreUiEvent.RetryClicked)
        }
    }

    override fun render(state: ShowMoreUiState) {
        binding.toolbar.title = state.title
        binding.progressBar.isVisible = state.isLoading
        binding.lottieAnimation.isVisible = state.errors != null
        binding.buttonRetry.isVisible = state.errors != null

        state.errors?.let {
            binding.lottieAnimation.setAnimation(it.animationRes)
            binding.lottieAnimation.playAnimation()
        }

        val flow = when (state.showMoreType) {
            ShowMoreType.POPULAR_MOVIES -> state.showMorePopularMovies
            ShowMoreType.TOP_RATED_MOVIES -> state.showMoreTopRatedMovies
            ShowMoreType.TRENDING_MOVIES -> state.showMoreTrendingMovies
            ShowMoreType.AIRING_TODAY_TV -> state.showMoreAiringTodayTvShow
            ShowMoreType.TOP_RATED_TV -> state.showMoreTopRatedTvShow
            ShowMoreType.POPULAR_TV -> state.showMorePopularTvShow
            ShowMoreType.ON_THE_AIR_TV -> state.showMoreOnTheAirTvShow
        }
        collectFlow(flow) { itemsPagingData ->
            showMoreAdapter.submitData(itemsPagingData)
        }
        collectFlow(showMoreAdapter.loadStateFlow) { viewModel.setErrorUiState(it) }
    }

    override fun onEffect(effect: ShowMoreUiEffect) {
        when (effect) {
            is ShowMoreUiEffect.NavigateToMovieDetails -> navigator.navigateToMovieDetails(effect.id)
            is ShowMoreUiEffect.NavigateToTvShowDetails -> navigator.navigateToTvDetails(effect.id)
            ShowMoreUiEffect.NavigateBack -> navigator.navigateBack()
            is ShowMoreUiEffect.ShowSnackBar -> showSnackBar(effect.message)
        }
    }

}
