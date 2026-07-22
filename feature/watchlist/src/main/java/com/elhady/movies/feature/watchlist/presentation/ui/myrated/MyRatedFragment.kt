package com.elhady.movies.feature.watchlist.presentation.ui.myrated

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.elhady.movies.feature.watchlist.BR
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.core.ui.adapters.BaseFooterAdapter
import com.elhady.movies.core.common.bases.BaseFragment
import com.elhady.movies.feature.watchlist.databinding.FragmentMyRatedBinding
import com.elhady.movies.feature.watchlist.presentation.myrated.MyRatedEvents
import com.elhady.movies.feature.watchlist.presentation.myrated.MyRatedUiState
import com.elhady.movies.feature.watchlist.presentation.myrated.MyRatedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyRatedFragment : BaseFragment<FragmentMyRatedBinding, MyRatedUiState, MyRatedEvents>() {

    override val layoutIdFragment: Int = R.layout.fragment_my_rated
    override val viewModel: MyRatedViewModel by viewModels()
    override val viewModelVariableId: Int = BR.viewModel
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
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    val flow = state.myRatedMedia
                    launch {
                        flow.collectLatest { itemsPagingData ->
                            myRateAdapter.submitData(itemsPagingData)
                        }
                    }
                    launch {
                        myRateAdapter.loadStateFlow.collectLatest {
                            viewModel.setErrorUiState(it)
                        }
                    }
                }
            }
        }
    }
    override fun onEvent(event: MyRatedEvents) {
        when(event){
            is MyRatedEvents.NavigateToMovieDetails -> {
                val request = NavDeepLinkRequest.Builder
                    .fromUri(Uri.parse("movie://movie_details/${event.movieId}"))
                    .build()
                findNavController().navigate(request)
            }
            is MyRatedEvents.NavigateToTVShowDetails -> {
                val request = NavDeepLinkRequest.Builder
                    .fromUri(Uri.parse("movie://tv_details/${event.tvId}"))
                    .build()
                findNavController().navigate(request)
            }
            is MyRatedEvents.NavigateBack -> findNavController().popBackStack()
            is MyRatedEvents.ShowMyRatedMoviesPressed -> viewModel.fetchMyRatedMovies()
            is MyRatedEvents.ShowMyRatedTvShowPressed -> viewModel.fetchMyRatedTvShow()
        }
    }
}
