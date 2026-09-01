package com.elhady.movies.feature.search.presentation.search

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.search.R
import com.elhady.movies.feature.search.databinding.FragmentSearchBinding
import com.elhady.movies.feature.search.presentation.search.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchUiState, SearchUiEffect>(),
    SearchAdapterListener, SearchFragmentListener, SearchFilterListener {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int = R.layout.fragment_search
    override val viewModel by activityViewModels<SearchViewModel>()

    private lateinit var searchAdapter: SearchAdapter
    private var historyAdapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listener = this
        setupHomeAdapter()
        setupSearchInput()
    }

    private fun setupHomeAdapter() {
        searchAdapter = SearchAdapter(this)
        binding.recyclerViewSearch.adapter = searchAdapter
    }

    private fun setupSearchInput() {
        binding.edittextSearch.addTextChangedListener {
            viewModel.onEvent(SearchUiEvent.QueryChanged(it.toString()))
        }
    }

    override fun render(state: SearchUiState) {
        setupSearchHistoryAdapter(state)

        val searchItems = when (state.mediaType) {
            SearchUiState.SearchMedia.MOVIE, SearchUiState.SearchMedia.TV -> {
                state.searchMediaResult.map { SearchItem.MediaItem(it) }
            }

            SearchUiState.SearchMedia.PEOPLE -> {
                state.searchPeopleResult.map { SearchItem.PeopleItem(it) }
            }
        }
        searchAdapter.submitList(searchItems)
        binding.state = state
    }

    private fun setupSearchHistoryAdapter(state: SearchUiState) {
        if (historyAdapter == null) {
            historyAdapter = ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_dropdown_item_1line,
                mutableListOf<String>()
            )
            binding.edittextSearch.setAdapter(historyAdapter)
        }
        historyAdapter?.apply {
            clear()
            addAll(state.searchHistory)
            notifyDataSetChanged()
        }
    }

    override fun onEffect(effect: SearchUiEffect) {
        when (effect) {
            is SearchUiEffect.OpenFilterBottomSheet -> showBottomSheet()
            is SearchUiEffect.ShowSnackBar -> showSnackBar(effect.message)
            is SearchUiEffect.NavigateToMovieDetails -> navigator.navigateToMovieDetails(effect.id)
            is SearchUiEffect.NavigateToPeopleDetails -> navigator.navigateToPeopleDetails(effect.id)
            is SearchUiEffect.NavigateToTvDetails -> navigator.navigateToTvDetails(effect.id)
            SearchUiEffect.NavigateBack -> navigator.navigateBack()
        }
    }

    private fun showBottomSheet() {
        FilterMovieAdapterBottomSheetFragment().show(childFragmentManager, "BOTTOM")
    }

    override fun onClickFilter() {
        viewModel.onEvent(SearchUiEvent.FilterClicked)
    }

    override fun onClickGenre(genreId: Int) {
        viewModel.onEvent(SearchUiEvent.GenreClicked(genreId))
    }

    override fun onClickClear() {
        viewModel.onEvent(SearchUiEvent.ClearClicked)
    }

    override fun showResultMovie() {
        viewModel.onEvent(SearchUiEvent.MediaTypeMovieClicked)
    }

    override fun showResultTv() {
        viewModel.onEvent(SearchUiEvent.MediaTypeTvClicked)
    }

    override fun showResultPeople() {
        viewModel.onEvent(SearchUiEvent.MediaTypePeopleClicked)
    }

    override fun onClickBack() {
        viewModel.onEvent(SearchUiEvent.BackClicked)
    }

    override fun onClickMovie(id: Int) {
        viewModel.onEvent(SearchUiEvent.MovieClicked(id))
    }

    override fun onClickPeople(id: Int) {
        viewModel.onEvent(SearchUiEvent.PeopleClicked(id))
    }

    override fun onClickTryAgain() {
        viewModel.onEvent(SearchUiEvent.TryAgainClicked)
    }

    override fun onClickApply() {
        viewModel.onEvent(SearchUiEvent.ApplyFilterClicked)
    }
}
