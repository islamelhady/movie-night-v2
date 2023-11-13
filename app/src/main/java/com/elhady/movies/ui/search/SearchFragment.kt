package com.elhady.movies.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.Orientation
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentSearchBinding
import com.elhady.movies.ui.adapter.LoadAdapter
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.ui.seriesDetails.SeriesDetailsFragmentDirections
import com.elhady.movies.utilities.Constants
import com.elhady.movies.utilities.collect
import com.elhady.movies.utilities.collectLast
import com.elhady.movies.utilities.setSpanSize
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override val layoutIdFragment: Int = R.layout.fragment_search
    override val viewModel: SearchViewModel by viewModels()

    private val mediaSearchAdapter by lazy { MediaSearchAdapter(viewModel) }
    private val actorSearchAdapter by lazy { ActorSearchAdapter(viewModel) }

    private val oldValue = MutableStateFlow(SearchUiState())



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSearchResultsBySearchTerm()
        collectEvent()
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
        getMediaSearch()
    }

    private fun getMediaSearch(){
        viewLifecycleOwner.lifecycleScope.launch {
            collectLast(viewModel.searchUiState.value.moviesSearchResult){
                mediaSearchAdapter.submitData(it)
            }
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

        getActorSearch()
    }

    private fun getActorSearch(){
        collectLast(viewModel.searchUiState.value.moviesSearchResult){
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
        when(viewModel.searchUiState.value.mediaType){
            MediaTypes.ACTORS -> bindActors()
            else -> bindMedia()
        }
    }
    private fun collectEvent(){
        collectLast(viewModel.searchUiEvent){ event->
            event?.getContentIfNotHandled()?.let {
                onEvent(it)
            }
        }
    }

    private fun onEvent(event: SearchUiEvent) {
        when(event){
            SearchUiEvent.ClickBackEvent -> findNavController().popBackStack()
            SearchUiEvent.ClickRetryEvent -> TODO()
            is SearchUiEvent.ClickActorEvent -> findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToActorDetailsFragment(event.actorId))
            is SearchUiEvent.ClickMediaEvent -> {
                when(event.mediaUiState.mediaTypes){
                   Constants.MOVIE -> navigateToMovies(event.mediaUiState.id)
                   Constants.TV_SERIES_SHOW -> navigateToSeries(event.mediaUiState.id)
                }
            }
        }
    }

    private fun navigateToMovies(movieId: Int){
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToMovieDetailsFragment(movieId))
    }

    private fun navigateToSeries(seriesId: Int){
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToTvShowDetailsFragment(seriesId))
    }

    @OptIn(FlowPreview::class)
    private fun getSearchResultsBySearchTerm() {
        lifecycleScope.launch {
            viewModel.searchUiState.debounce(500).collectLatest { searchTerm ->
                if (searchTerm.inputSearch.isNotBlank() && oldValue.value.inputSearch != viewModel.searchUiState.value.inputSearch
                    || oldValue.value.mediaType != viewModel.searchUiState.value.mediaType) {
                    getSearchResult()
                    oldValue.emit(viewModel.searchUiState.value)
                }
            }
        }
    }


}