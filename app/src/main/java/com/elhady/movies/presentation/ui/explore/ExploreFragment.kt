package com.elhady.movies.presentation.ui.explore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseFragment
import com.elhady.movies.databinding.FragmentExploreBinding
import com.elhady.movies.presentation.viewmodel.explore.ExploreItem
import com.elhady.movies.presentation.viewmodel.explore.ExploreUiEvent
import com.elhady.movies.presentation.viewmodel.explore.ExploreUiState
import com.elhady.movies.presentation.viewmodel.explore.ExploreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding, ExploreUiState, ExploreUiEvent>() {

    override val layoutIdFragment: Int = R.layout.fragment_explore
    override val viewModel: ExploreViewModel by viewModels()
    private lateinit var adapter: ExploreAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectData()
        setAdapter()
    }

    private fun setAdapter() {
        adapter = ExploreAdapter(mutableListOf(), viewModel)
        binding.recyclerTrend.adapter = adapter
    }

    private fun collectData() {
        collectLatest {
            viewModel.state.collect { state ->
                val exploreItem = if (state.layoutManager) {
                    state.trendingMoviesToday.map { ExploreItem.GridItem(it) }
                } else {
                    state.trendingMoviesToday.map { ExploreItem.HorizontalItem(it) }
                }
                adapter.setItems(exploreItem)
            }
        }
    }

    override fun onEvent(event: ExploreUiEvent) {
        when (event) {
            ExploreUiEvent.NavigateToSearchEvent -> navigateToSearch()
            is ExploreUiEvent.ShowSnackBarMessageEvent -> showSnackBar(event.message)
            is ExploreUiEvent.NavigateToMovieDetailsEvent -> navigateToMovieDetails(event.movieId)
        }
    }

    private fun navigateToMovieDetails(id: Int) {
        findNavController().navigate(
            ExploreFragmentDirections.actionExploreFragmentToMovieDetailsFragment(
                movieId = id
            )
        )
    }


    private fun navigateToSearch() {
        val extras = FragmentNavigatorExtras(binding.inputSearch to "search_box")
        Navigation.findNavController(binding.root)
            .navigate(
                ExploreFragmentDirections.actionExploreFragmentToSearchFragment(),
                extras
            )
    }

}