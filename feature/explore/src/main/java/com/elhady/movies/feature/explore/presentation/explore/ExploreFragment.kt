package com.elhady.movies.feature.explore.presentation.explore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
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
            listener = this
        )
        binding.recyclerTrend.adapter = adapter
    }


    override fun render(
        state: ExploreUiState
    ) {
        binding.state = state
    }

    override fun onEffect(effect: ExploreUiEffect) {
        when (effect) {
            ExploreUiEffect.NavigateToSearch -> navigator.navigateToSearch()
            is ExploreUiEffect.ShowSnackBar -> showSnackBar(effect.message)
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
