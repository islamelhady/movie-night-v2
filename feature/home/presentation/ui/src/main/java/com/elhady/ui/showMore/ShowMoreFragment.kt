package com.elhady.ui.showMore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.elhady.base.BaseFragment
import com.elhady.ui.R
import com.elhady.ui.databinding.FragmentShowMoreBinding
import com.elhady.viewmodel.showMore.SeeAllMediaUiEvent
import com.elhady.viewmodel.showMore.ShowMoreType
import com.elhady.viewmodel.showMore.ShowMoreUiState
import com.elhady.viewmodel.showMore.ShowMoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowMoreFragment : BaseFragment<FragmentShowMoreBinding, ShowMoreUiState, SeeAllMediaUiEvent>() {

    override val layoutIdFragment: Int = R.layout.fragment_show_more
    override val viewModel: ShowMoreViewModel by viewModels()
    private val showMoreAdapter by lazy { ShowMoreAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    private fun setAdapter() {
        binding.recyclerAllMedia.adapter = showMoreAdapter


        collectLatest {
            viewModel.state.collectLatest { state ->
                val flow = when (state.showMoreType) {
                    ShowMoreType.POPULAR_TV_SHOW -> state.showMorePopularTvShows
                    ShowMoreType.TOP_RATED_TV_SHOW -> state.showMoreTopRatedTvShows
                    ShowMoreType.LATEST_TV_SHOW -> state.showMoreLatestTvShows
                    ShowMoreType.ON_THE_AIR_TV_SHOW -> state.showMoreOnTheAirMovies
                    ShowMoreType.TOP_RATED_MOVIES -> state.showMoreTopRatedMovies
                    ShowMoreType.ADVENTURE_MOVIES -> state.showMoreAdventureMovies
                    ShowMoreType.MYSTERY_MOVIES -> state.showMoreMysteryMovies
                    ShowMoreType.TRENDING_MOVIES -> state.showMoreTrendingMovies
                    ShowMoreType.UPCOMING_MOVIES -> state.showMoreUpcomingMovies
                    ShowMoreType.NOW_PLAYING_MOVIES -> state.showMoreNowPlayingMovies
                    ShowMoreType.POPULAR_ACTORS -> state.showMorePopularActorMovies
                }
                collectLast(flow) { itemsPagingData ->
                    showMoreAdapter.submitData(itemsPagingData)
                }
                collectLast(showMoreAdapter.loadStateFlow) { viewModel.setErrorUiState(it) }

            }
        }
    }

    private fun <T> LifecycleOwner.collectLast(flow: Flow<T>, action: suspend (T) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest(action)
            }
        }
    }


    override fun onEvent(event: SeeAllMediaUiEvent) {
        when (event) {
            is SeeAllMediaUiEvent.ClickMovieEvent -> {
                findNavController().navigate(
                    ShowMoreFragmentDirections.actionAllMediaFragmentToMovieDetailsFragment(
                        event.mediaId
                    )
                )
            }

            is SeeAllMediaUiEvent.ClickSeriesEvent -> {
                findNavController().navigate(
                    ShowMoreFragmentDirections.actionAllMediaFragmentToTvShowDetailsFragment(
                        event.mediaId
                    )
                )
            }

            is SeeAllMediaUiEvent.ShowSnackBar -> showSnackBar(event.message)
        }
    }
}