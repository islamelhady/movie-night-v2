package com.elhady.movies.presentation.ui.myrated

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseFooterAdapter
import com.elhady.movies.core.bases.BaseFragment
import com.elhady.movies.databinding.FragmentMyRatedBinding
import com.elhady.movies.presentation.ui.tvshows.collectLast
import com.elhady.movies.presentation.viewmodel.myrated.MyRatedEvents
import com.elhady.movies.presentation.viewmodel.myrated.MyRatedUiState
import com.elhady.movies.presentation.viewmodel.myrated.MyRatedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyRatedFragment : BaseFragment<FragmentMyRatedBinding, MyRatedUiState, MyRatedEvents>() {

    override val layoutIdFragment: Int = R.layout.fragment_my_rated
    override val viewModel: MyRatedViewModel by viewModels()
    private val myRateAdapter by lazy { MyRateAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        getData()
    }
    private fun setAdapter() {
        val footerAdapter = BaseFooterAdapter { myRateAdapter.retry() }
        binding.recyclerViewMedia.adapter = myRateAdapter.withLoadStateFooter(footerAdapter)
    }

    private fun getData(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                val flow = state.myRatedMedia
                collectLast(flow) { itemsPagingData ->
                    viewLifecycleOwner.lifecycleScope.launch {
                        myRateAdapter.submitData(itemsPagingData)
                    }
                }
                collectLast(myRateAdapter.loadStateFlow) { viewModel.setErrorUiState(it) }
            }
        }
    }
    override fun onEvent(event: MyRatedEvents) {
        when(event){
            is MyRatedEvents.NavigateToMovieDetails -> findNavController().navigate(MyRatedFragmentDirections.actionMyRatedFragmentToMovieDetailsFragment(event.movieId))
            is MyRatedEvents.NavigateToTVShowDetails -> findNavController().navigate(MyRatedFragmentDirections.actionMyRatedFragmentToMovieDetailsFragment(event.tvId))
            is MyRatedEvents.NavigateBack -> findNavController().popBackStack()
            is MyRatedEvents.ShowMyRatedMoviesPressed -> viewModel.fetchMyRatedMovies()
            is MyRatedEvents.ShowMyRatedTvShowPressed -> viewModel.fetchMyRatedTvShow()
        }
    }
}