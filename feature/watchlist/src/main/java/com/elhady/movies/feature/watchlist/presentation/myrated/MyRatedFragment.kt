package com.elhady.movies.feature.watchlist.presentation.myrated

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.feature.watchlist.BR
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.core.ui.adapter.BaseFooterAdapter
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.feature.watchlist.databinding.FragmentMyRatedBinding
import com.elhady.movies.feature.watchlist.presentation.myrated.adapter.MyRateAdapter
import com.elhady.movies.core.ui.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MyRatedFragment : BaseFragment<FragmentMyRatedBinding, MyRatedUiState, MyRatedUiEvent>() {

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

    private fun getData() {
        collectFlow(myRateAdapter.loadStateFlow) {
            viewModel.setErrorUiState(it)
        }
        collectFlow(viewModel.state) { state ->
            state.movies.collectLatest { itemsPagingData ->
                myRateAdapter.submitData(itemsPagingData)
            }
        }
    }
    override fun onEffect(effect: MyRatedUiEvent) {
        when(effect){
            is MyRatedUiEvent.NavigateToMovieDetails -> {
                navigator.navigateToMovieDetails(effect.movieId)
            }
            is MyRatedUiEvent.NavigateToTvShowDetails -> {
                navigator.navigateToTvDetails(effect.tvId)
            }
            is MyRatedUiEvent.NavigateBack -> navigator.navigateBack()
            is MyRatedUiEvent.ShowMyRatedMoviesPressed -> viewModel.fetchMyRatedMovies()
            is MyRatedUiEvent.ShowMyRatedTvShowPressed -> viewModel.fetchMyRatedTvShow()
        }
    }
}
