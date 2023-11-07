package com.elhady.movies.ui.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentCategoryBinding
import com.elhady.movies.ui.adapter.LoadAdapter
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.ui.models.MediaUiState
import com.elhady.movies.utilities.Constants
import com.elhady.movies.utilities.collect
import com.elhady.movies.utilities.collectLast
import com.elhady.movies.utilities.setSpanSize
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_category
    override val viewModel: CategoryViewModel by viewModels()
    private val categoryAdapter by lazy { CategoryAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
//        binding.viewModel = viewModel
        collectEvent()
        collectData()
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
    }

   private fun collectData(){
       viewLifecycleOwner.lifecycleScope.launch {
           viewModel.categoryUiState.collect{
               collectLast(
                   flow = viewModel.categoryUiState.value.moviesResult,
                   action = {
                       categoryAdapter.submitData(it)
                   })
           }
       }

   }


    private fun collectEvent() {
        collectLast(viewModel.categoryUiEvent){ event ->
            event?.getContentIfNotHandled()?.let {
                onEvent(it)
            }
        }
    }

    private fun onEvent(event: CategoryUiEvent) {
        when(event){
            is CategoryUiEvent.ClickCategoryEvent -> viewModel.getListMovies(event.categoryId)
            CategoryUiEvent.ClickRetry -> categoryAdapter.retry()
        }

    }


}