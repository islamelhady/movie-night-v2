package com.elhady.ui.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.elhady.base.BaseFragment
import com.elhady.ui.R
import com.elhady.ui.adapter.LoadAdapter
import com.elhady.ui.databinding.FragmentCategoryBinding
import com.elhady.viewmodel.category.CategoryUiEvent
import com.elhady.viewmodel.category.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding, CategoryUiState, CategoryUiEvent>() {
    override val layoutIdFragment: Int = R.layout.fragment_category
    override val viewModel: CategoryViewModel by viewModels()
    private val categoryAdapter by lazy { CategoryAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(true, getTitle())
        setAdapter()
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
           viewModel.state.collect{
               collectLast(
                   flow = viewModel.state.value.moviesResult,
                   action = {
                       categoryAdapter.submitData(it)
                   })
           }
       }

   }

    override fun onEvent(event: CategoryUiEvent) {
        when(event){
            is CategoryUiEvent.ClickCategoryEvent -> viewModel.getMediaList(event.categoryId)
            CategoryUiEvent.ClickRetry -> categoryAdapter.retry()
            is CategoryUiEvent.ClickMediaEvent -> navigateToDetails(event.mediaId)
        }
    }

    private fun navigateToDetails(mediaId: Int) {
        when(viewModel.args.mediaType){
            MediaType.MOVIES -> findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToMovieDetailsFragment(mediaId))
            MediaType.SERIES -> findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToTvShowDetailsFragment(mediaId))
        }

    }

    private fun getTitle(): String{
        return when(viewModel.args.mediaType){
            MediaType.MOVIES -> resources.getString(R.string.movies)
            MediaType.SERIES -> resources.getString(R.string.tv_series)
        }
    }

}