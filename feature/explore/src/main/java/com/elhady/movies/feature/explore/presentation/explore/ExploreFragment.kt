package com.elhady.movies.feature.explore.presentation.explore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.explore.R
import com.elhady.movies.feature.explore.databinding.FragmentExploreBinding
import com.elhady.movies.feature.explore.presentation.explore.adapter.ExploreAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding, ExploreUiState, ExploreUiEffect>(),
    ExploreListener, ExploreAdapterListener {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int = R.layout.fragment_explore
    override val viewModel: ExploreViewModel by viewModels()
    private lateinit var adapter: ExploreAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listener = this
        setAdapter()
    }

    private fun setAdapter() {
        adapter = ExploreAdapter(
            items = mutableListOf(),
            listener = this
        )
        binding.recyclerTrend.adapter = adapter
    }


    override fun render(
        state: ExploreUiState
    ) {
        binding.state = state
        renderMovies(state)
        renderLayout(state)
        renderError(state)
    }

    private fun renderError(
        state: ExploreUiState
    ) {
        val errors = state.errors
        val hasNoData = state.trendingMoviesToday.isEmpty()

        if (errors == null || !hasNoData) {
            binding.errorAnimation.cancelAnimation()
            return
        }

        binding.errorAnimation.setAnimation(errors.animationRes)
        binding.errorAnimation.playAnimation()
    }

    private fun renderMovies(
        state: ExploreUiState
    ) {
        val items = if (state.isGridLayout) {
            state.trendingMoviesToday.map {
                ExploreItem.GridItem(it)
            }
        } else {
            state.trendingMoviesToday.map {
                ExploreItem.HorizontalItem(it)
            }
        }
        adapter.setItems(items)
    }

    private fun renderLayout(
        state: ExploreUiState
    ) {
        val currentLayoutManager = binding.recyclerTrend.layoutManager
        val isGrid = currentLayoutManager is GridLayoutManager
        
        if (state.isGridLayout != isGrid || currentLayoutManager == null) {
            binding.recyclerTrend.layoutManager = if (state.isGridLayout) {
                GridLayoutManager(requireContext(), 2)
            } else {
                LinearLayoutManager(requireContext())
            }
        }
    }

    override fun onEffect(effect: ExploreUiEffect) {
        when (effect) {
            ExploreUiEffect.NavigateToSearch -> navigator.navigateToSearch()
            is ExploreUiEffect.ShowSnackBar -> showSnackBar(getString(effect.messageRes))
            is ExploreUiEffect.NavigateToMovieDetails -> navigator.navigateToMovieDetails(effect.movieId)
        }
    }

    override fun onClickSearch() {
        viewModel.onEvent(ExploreUiEvent.SearchClicked)
    }

    override fun onClickChangeLayout() {
        viewModel.onEvent(ExploreUiEvent.ChangeLayoutClicked)
    }

    override fun onClickRetry() {
        viewModel.onEvent(ExploreUiEvent.RetryClicked)
    }

    override fun onClickMovie(id: Int) {
        viewModel.onEvent(ExploreUiEvent.MovieClicked(id))
    }

}
