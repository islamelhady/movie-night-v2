package com.elhady.movies.feature.explore.presentation.explore

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
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
class ExploreFragment : BaseFragment<FragmentExploreBinding, ExploreUiState, ExploreUiEffect>() {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int = R.layout.fragment_explore
    override val viewModel: ExploreViewModel by viewModels()
    private lateinit var adapter: ExploreAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setListener()
    }

    private fun setListener() {
        binding.inputSearch.setOnClickListener {
            viewModel.onEvent(ExploreUiEvent.SearchClicked)
        }
        binding.switchCompat.setOnClickListener {
            viewModel.onEvent(ExploreUiEvent.ChangeLayoutClicked)
        }
        binding.buttonRetry.setOnClickListener {
            viewModel.onEvent(ExploreUiEvent.RetryClicked)
        }
    }


    private fun setAdapter() {
        adapter = ExploreAdapter(
            list = mutableListOf(),
            listener = object : ExploreAdapterListener {
                override fun onClickMovie(id: Int) {
                    viewModel.onEvent(
                        ExploreUiEvent.MovieClicked(id)
                    )
                }
            }
        )
        binding.recyclerTrend.adapter = adapter
    }


    override fun render(
        state: ExploreUiState
    ) {
        renderLoading(state)
        renderMovies(state)
        renderLayout(state)
        renderError(state)
    }

    private fun renderLoading(
        state: ExploreUiState
    ) {
        binding.animationLoading.isVisible = state.isLoading
    }

    private fun renderError(
        state: ExploreUiState
    ) {
        val errors = state.errors
        binding.errorAnimation.isVisible = errors != null
        binding.buttonRetry.isVisible = errors != null

        if (errors == null) {
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
        binding.recyclerTrend.isVisible =
            state.trendingMoviesToday.isNotEmpty() && !state.isLoading && state.errors == null

        adapter.setItems(items)
    }

    private fun renderLayout(
        state: ExploreUiState
    ) {
        binding.switchCompat.isChecked = state.isGridLayout
        binding.recyclerTrend.layoutManager =
            if (state.isGridLayout) {
                GridLayoutManager(
                    requireContext(),
                    2
                )
            } else {
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }
    }

    override fun onEffect(effect: ExploreUiEffect) {
        when (effect) {
            ExploreUiEffect.NavigateToSearch -> navigateToSearch()
            is ExploreUiEffect.ShowSnackBar -> showSnackBar(effect.message)
            is ExploreUiEffect.NavigateToMovieDetails -> navigator.navigateToMovieDetails(effect.movieId)
        }
    }


    private fun navigateToSearch() {
        // Keeping extras for now, using findNavController directly for infrastructure
        val extras = FragmentNavigatorExtras(binding.inputSearch to "search_box")
        val request =
            NavDeepLinkRequest.Builder.fromUri(Uri.parse("movie://search")).build()

        findNavController().navigate(request, null, extras)
    }

}
