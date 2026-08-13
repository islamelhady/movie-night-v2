package com.elhady.movies.feature.showmore.presentation.showmore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.elhady.movies.feature.showmore.BR
import com.elhady.movies.feature.showmore.R
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.feature.showmore.databinding.FragmentShowMoreBinding
import com.elhady.movies.core.common.ShowMoreType
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.showmore.presentation.showmore.adapter.ShowMoreAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowMoreFragment : BaseFragment<FragmentShowMoreBinding, ShowMoreUiState, ShowMoreUiEvent>() {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int = R.layout.fragment_show_more
    override val viewModel: ShowMoreViewModel by viewModels()
    override val viewModelVariableId: Int = BR.viewModel
    private val showMoreAdapter by lazy { ShowMoreAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    private fun setAdapter() {

        binding.recyclerMedia.adapter = showMoreAdapter

        collectFlow(flow = viewModel.state) { state ->
                val flow = when (state.showMoreType) {
                    ShowMoreType.POPULAR_MOVIES -> state.showMorePopularMovies
                    ShowMoreType.TOP_RATED_MOVIES -> state.showMoreTopRatedMovies
                    ShowMoreType.TRENDING_MOVIES -> state.showMoreTrendingMovies
                    ShowMoreType.AIRING_TODAY_TV -> state.showMoreAiringTodayTvShow
                    ShowMoreType.TOP_RATED_TV -> state.showMoreTopRatedTvShow
                    ShowMoreType.POPULAR_TV -> state.showMorePopularTvShow
                    ShowMoreType.ON_THE_AIR_TV -> state.showMoreOnTheAirTvShow
                }
                collectLast(flow) { itemsPagingData ->
                    showMoreAdapter.submitData(itemsPagingData)
                }
                collectLast(showMoreAdapter.loadStateFlow) { viewModel.setErrorUiState(it) }

            }

    }

    private fun <T> LifecycleOwner.collectLast(flow: Flow<T>, action: suspend (T) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest(action)
            }
        }
    }

    override fun onEffect(effect: ShowMoreUiEvent) {
        when (effect) {
            is ShowMoreUiEvent.NavigateToMovieDetailsEvent -> navigator.navigateToMovieDetails(effect.itemId)

            is ShowMoreUiEvent.NavigateToTvShowDetailsEvent -> navigator.navigateToTvDetails(effect.itemId)
            ShowMoreUiEvent.BackNavigateEvent -> navigator.navigateBack()
            is ShowMoreUiEvent.ShowSnackBarEvent -> showSnackBar(effect.messages)
        }
    }

}
