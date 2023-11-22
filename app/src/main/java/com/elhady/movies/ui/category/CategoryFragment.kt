package com.elhady.movies.ui.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentCategoryBinding
import com.elhady.movies.domain.enums.MediaType
import com.elhady.movies.ui.adapter.LoadAdapter
import com.elhady.movies.ui.base.BaseFragment
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
        setTitle(true, getTitle())
        setAdapter()
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
           viewModel.state.collect{
               collectLast(
                   flow = viewModel.state.value.moviesResult,
                   action = {
                       categoryAdapter.submitData(it)
                   })
           }
       }

   }


    private fun collectEvent() {
        collectLast(viewModel.categoryUiEvent){ event ->
            event.getContentIfNotHandled()?.let {
                onEvent(it)
            }
        }
    }

    private fun onEvent(event: CategoryUiEvent) {
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