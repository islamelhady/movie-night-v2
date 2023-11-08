package com.elhady.movies.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentSearchBinding
import com.elhady.movies.ui.adapter.LoadAdapter
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.utilities.collectLast
import com.elhady.movies.utilities.setSpanSize
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override val layoutIdFragment: Int = R.layout.fragment_search
    override val viewModel: SearchViewModel by viewModels()
    private val mediaSearchAdapter by lazy { MediaSearchAdapter(viewModel) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        collectData()
    }

    private fun setAdapter() {
        val footerAdapter = LoadAdapter(mediaSearchAdapter::retry)
        binding.recyclerSearch.adapter =
            mediaSearchAdapter.withLoadStateFooter(footer = footerAdapter)

        val gridLayoutManager = binding.recyclerSearch.layoutManager as GridLayoutManager
        gridLayoutManager.setSpanSize(
            footerAdapter = footerAdapter,
            adapter = mediaSearchAdapter,
            spanCount = gridLayoutManager.spanCount
        )

        collectLast(mediaSearchAdapter.loadStateFlow){
            viewModel.setError(it)
        }
    }

    private fun collectData(){
        viewLifecycleOwner.lifecycleScope.launch {
            collectLast(viewModel.searchUiState.value.moviesSearchResult){
                mediaSearchAdapter.submitData(it)
            }
        }
    }




}