package com.elhady.movies.ui.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentCategoryBinding
import com.elhady.movies.ui.adapter.LoadAdapter
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.ui.models.MediaUiState
import com.elhady.movies.utilities.collect
import com.elhady.movies.utilities.collectLast
import com.elhady.movies.utilities.setSpanSize
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_category
    override val viewModel: CategoryViewModel by viewModels()
    private val categoryAdapter by lazy { CategoryAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    private fun setAdapter() {
        val footerAdapter = LoadAdapter(categoryAdapter::retry)
        binding.recyclerViewCategory.adapter = categoryAdapter.withLoadStateFooter(footerAdapter)

        val layoutManager = binding.recyclerViewCategory.layoutManager as GridLayoutManager
        layoutManager.setSpanSize(
            footerAdapter = footerAdapter,
            adapter = categoryAdapter,
            spanCount = layoutManager.spanCount
        )

        collect(flow = categoryAdapter.loadStateFlow, action = { viewModel.setErrorUiState(it)})

        collectLast(flow = viewModel.categoryUiState.value.moviesResult, action = ::setMovies)
    }

    private suspend fun setMovies(itemsPagingData: PagingData<MediaUiState>){
        categoryAdapter.submitData(pagingData = itemsPagingData)

    }




}