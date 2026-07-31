package com.elhady.movies.feature.search.presentation.search

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.search.BR
import com.elhady.movies.feature.search.R
import com.elhady.movies.feature.search.databinding.FragmentSearchBinding
import com.elhady.movies.feature.search.presentation.search.adapter.SearchAdapter
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchUiState, SearchUiEvent>() {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int = R.layout.fragment_search
    override val viewModel by activityViewModels<SearchViewModel>()
    override val viewModelVariableId: Int = BR.viewModel

    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHomeAdapter()
        collectChange()
        doNothingWhenTheSameChipIsReslected()
    }

    private fun setupHomeAdapter() {
        searchAdapter = SearchAdapter(mutableListOf(), viewModel)
        binding.recyclerViewSearch.adapter = searchAdapter
    }

    private fun collectChange() {
        collectFlow(flow = viewModel.state) { state ->
                setupSearchHistoryAdapter(state)

                val searchItems = when (state.mediaType) {
                    SearchUiState.SearchMedia.MOVIE, SearchUiState.SearchMedia.TV -> {
                        state.searchMediaResult.map { SearchItem.MediaItem(it) }
                    }

                    SearchUiState.SearchMedia.PEOPLE -> {
                        state.searchPeopleResult.map { SearchItem.PeopleItem(it) }
                    }
                }
                searchAdapter.setItems(searchItems)
                state.error?.last()?.let { showSnackBar(it) }
            }

    }

    private fun setupSearchHistoryAdapter(state: SearchUiState) {
        val searchHistory = state.searchHistory.map { it }
        val adapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_dropdown_item_1line,
            searchHistory
        )
        binding.edittextSearch.setAdapter(adapter)
    }

    override fun onEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.OpenFilterBottomSheet -> showBottomSheet()
            is SearchUiEvent.ApplyFilter -> applyFilter(event.genre)
            is SearchUiEvent.ShowSnackBar -> showSnackBar(event.messages)
            is SearchUiEvent.NavigateToMovie -> navigateToMovie(event.movieId)
            is SearchUiEvent.NavigateToPeople -> navigateToPeople(event.peopleId)
            is SearchUiEvent.NavigateToTv -> navigateToTv(event.tvId)
            is SearchUiEvent.ShowMovieResult -> showMovieResult()
            is SearchUiEvent.ShowTvResult -> showTvResult()
            is SearchUiEvent.ShowPeopleResult -> showPeopleResult()
            is SearchUiEvent.NavigateToBack -> navigateBack()
        }
    }

    private fun navigateBack() {
        navigator.navigateBack()
    }

    private fun showBottomSheet() {
        FilterMovieBottomSheetFragment().show(childFragmentManager, "BOTTOM")
    }

    private fun applyFilter(genresId: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.onClickGenre(genresId)
        }
    }

    private fun navigateToMovie(movieId: Int) {
        navigator.navigateToMovieDetails(movieId)
    }

    private fun navigateToPeople(peopleId: Int) {
        navigator.navigateToPeopleDetails(peopleId)
    }

    private fun navigateToTv(tvId: Int) {
        navigator.navigateToTvDetails(tvId)
    }

    private fun showMovieResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.onSearchForMovie()
        }
    }

    private fun showTvResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.onSearchForTv()
        }
    }

    private fun showPeopleResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.onSearchForPeople()
        }
    }

    private fun doNothingWhenTheSameChipIsReslected() {
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            if (chip?.isChecked == true) {
                // Do nothing when the same chip is reselected
            }
        }
    }
}
