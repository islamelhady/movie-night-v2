package com.elhady.movies.feature.watchlist.presentation.ui.myrated

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.elhady.movies.feature.watchlist.BR
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.core.ui.adapters.BaseFooterAdapter
import com.elhady.movies.core.ui.bases.BaseFragment
import com.elhady.movies.feature.watchlist.databinding.FragmentMyRatedBinding
import com.elhady.movies.feature.watchlist.presentation.myrated.MyRatedEvents
import com.elhady.movies.feature.watchlist.presentation.myrated.MyRatedUiState
import com.elhady.movies.feature.watchlist.presentation.myrated.MyRatedViewModel
import com.elhady.movies.core.domain.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyRatedFragment : BaseFragment<FragmentMyRatedBinding, MyRatedUiState, MyRatedEvents>() {

    @Inject
    lateinit var navigator: Navigator

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
                navigator.navigateToMovieDetails(event.movieId)
            }
            is MyRatedEvents.NavigateToTVShowDetails -> {
                navigator.navigateToTvDetails(event.tvId)
            }
            is MyRatedEvents.NavigateBack -> navigator.navigateBack()
            is MyRatedEvents.ShowMyRatedMoviesPressed -> viewModel.fetchMyRatedMovies()
            is MyRatedEvents.ShowMyRatedTvShowPressed -> viewModel.fetchMyRatedTvShow()
        }
    }
}
