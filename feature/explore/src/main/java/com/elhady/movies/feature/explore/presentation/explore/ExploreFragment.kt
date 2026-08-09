package com.elhady.movies.feature.explore.presentation.explore

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.explore.BR
import com.elhady.movies.feature.explore.R
import com.elhady.movies.feature.explore.databinding.FragmentExploreBinding
import com.elhady.movies.feature.explore.presentation.explore.adapter.ExploreAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding, ExploreUiState, ExploreUiEvent>() {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int = R.layout.fragment_explore
    override val viewModel: ExploreViewModel by viewModels()
    override val viewModelVariableId: Int = BR.viewModel
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
        collectFlow(flow = viewModel.state) { state ->
                val exploreItem = if (state.layoutManager) {
                    state.trendingMoviesToday.map { ExploreItem.GridItem(it) }
                } else {
                    state.trendingMoviesToday.map { ExploreItem.HorizontalItem(it) }
                }
                adapter.setItems(exploreItem)
            }

    }

    override fun onEffect(effect: ExploreUiEvent) {
        when (effect) {
            ExploreUiEvent.NavigateToSearchEvent -> navigateToSearch()
            is ExploreUiEvent.ShowSnackBarMessageEvent -> showSnackBar(effect.message)
            is ExploreUiEvent.NavigateToMovieDetailsEvent -> navigator.navigateToMovieDetails(effect.movieId)
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
