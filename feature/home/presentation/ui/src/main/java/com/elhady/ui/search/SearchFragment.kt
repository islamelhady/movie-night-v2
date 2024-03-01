package com.elhady.ui.search

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.elhady.base.BaseFragment
import com.elhady.ui.adapter.LoadAdapter
import com.elhady.movies.ui.search.SearchType
import com.elhady.movies.ui.search.SearchUiState
import com.elhady.ui.R
import com.elhady.ui.databinding.FragmentSearchBinding
import com.elhady.viewmodel.search.SearchUiEvent
import com.elhady.viewmodel.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchUiState, SearchUiEvent>() {

    override val layoutIdFragment: Int = R.layout.fragment_search
    override val viewModel: SearchViewModel by viewModels()

    private val mediaSearchAdapter by lazy { MediaSearchAdapter(viewModel) }
    private val actorSearchAdapter by lazy { ActorSearchAdapter(viewModel) }

    private val oldValue = MutableStateFlow(SearchUiState())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(false)
        getSearchResultsBySearchTerm()
        setHistoryAdapter()
    }

    private fun setHistoryAdapter() {
        binding.recyclerSearchHistory.adapter = HistorySearchAdapter(mutableListOf(), viewModel)
    }

    private fun bindMedia() {
        val footerAdapter = LoadAdapter(mediaSearchAdapter::retry)
        binding.recyclerSearch.adapter =
            mediaSearchAdapter.withLoadStateFooter(footer = footerAdapter)

        binding.recyclerSearch.layoutManager = LinearLayoutManager(this@SearchFragment.context, VERTICAL, false)

        collectLast(mediaSearchAdapter.loadStateFlow){
            viewModel.setError(it)
        }
        collectLast(viewModel.state.value.moviesSearchResult){
            mediaSearchAdapter.submitData(it)
        }
    }

    private fun bindActors(){
        val footerAdapter = LoadAdapter(actorSearchAdapter::retry)
        binding.recyclerSearch.adapter = actorSearchAdapter.withLoadStateFooter(footer = footerAdapter)

        binding.recyclerSearch.layoutManager = GridLayoutManager(this@SearchFragment.context, 3)
        setSpanSize(footerAdapter)

        collect(actorSearchAdapter.loadStateFlow){
            viewModel.setError(it)
        }

        collectLast(viewModel.state.value.moviesSearchResult){
            actorSearchAdapter.submitData(it)
        }
    }

    private fun setSpanSize(footerAdapter: LoadAdapter) {
        val mManager = binding.recyclerSearch.layoutManager as GridLayoutManager
        mManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if ((position == actorSearchAdapter.itemCount)
                    && footerAdapter.itemCount > 0
                ) {
                    mManager.spanCount
                } else {
                    1
                }
            }
        }
    }

    private fun getSearchResult(){
        when(viewModel.state.value.mediaType){
            SearchType.MOVIES, SearchType.TV -> bindMedia()
            SearchType.PEOPLE -> bindActors()
        }
    }

    override fun onEvent(event: SearchUiEvent) {
        when(event){
            SearchUiEvent.ClickBackEvent -> findNavController().popBackStack()
            is SearchUiEvent.ClickActorEvent -> findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToActorDetailsFragment(event.actorId))
            is SearchUiEvent.ClickMediaEvent -> {
                when(event.mediaUiState.mediaTypes){
                   Constants.MOVIE -> {findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToMovieDetailsFragment(event.mediaUiState.id))}
                    Constants.TV_SERIES_SHOW -> { findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToTvShowDetailsFragment(event.mediaUiState.id))}

                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun getSearchResultsBySearchTerm() {
        lifecycleScope.launch {
            viewModel.state.debounce(500).collectLatest { searchTerm ->
                if (searchTerm.inputSearch.isNotBlank() && oldValue.value.inputSearch != viewModel.state.value.inputSearch
                    || oldValue.value.mediaType != viewModel.state.value.mediaType) {
                    getSearchResult()
                    oldValue.emit(viewModel.state.value)
                }
            }
        }
    }


}