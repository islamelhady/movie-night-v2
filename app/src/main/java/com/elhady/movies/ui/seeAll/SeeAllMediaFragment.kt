package com.elhady.movies.ui.seeAll

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentSeeAllMediaBinding
import com.elhady.movies.domain.enums.SeeAllType
import com.elhady.movies.ui.adapter.LoadAdapter
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.ui.models.MediaUiState
import com.elhady.movies.utilities.collect
import com.elhady.movies.utilities.collectLast
import com.elhady.movies.utilities.setSpanSize
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeAllMediaFragment : BaseFragment<FragmentSeeAllMediaBinding, SeeAllMediaUiState, SeeAllMediaUiEvent>() {

    override val layoutIdFragment: Int = R.layout.fragment_see_all_media
    override val viewModel: SeeAllMediaViewModel by viewModels()
    private val allMediaAdapter by lazy {
        SeeAllMediaAdapter(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setTitle(true, getTitle())
    }

    private fun setAdapter() {
        val footerAdapter = LoadAdapter(allMediaAdapter::retry)
        binding.recyclerAllMedia.adapter = allMediaAdapter.withLoadStateFooter(footerAdapter)

        val layoutManager = binding.recyclerAllMedia.layoutManager as GridLayoutManager
        layoutManager.setSpanSize(
            footerAdapter = footerAdapter,
            adapter = allMediaAdapter,
            spanCount = layoutManager.spanCount
        )

        collect(flow = allMediaAdapter.loadStateFlow,
            action = { viewModel.setErrorUiState(it) })

        collectLast(flow = viewModel.state.value.allMedia, ::setAllMedia)
    }

    private suspend fun setAllMedia(itemsPagingData: PagingData<MediaUiState>) {
        allMediaAdapter.submitData(pagingData = itemsPagingData)
    }

    override fun onEvent(event: SeeAllMediaUiEvent) {
        when (event) {
            is SeeAllMediaUiEvent.ClickMovieEvent -> {
                findNavController().navigate(
                    SeeAllMediaFragmentDirections.actionAllMediaFragmentToMovieDetailsFragment(
                        event.mediaId
                    )
                )
            }

            is SeeAllMediaUiEvent.ClickSeriesEvent -> {
                findNavController().navigate(
                    SeeAllMediaFragmentDirections.actionAllMediaFragmentToTvShowDetailsFragment(
                        event.mediaId
                    )
                )
            }

            is SeeAllMediaUiEvent.ShowSnackBar -> showSnackBar(event.message)
        }
    }

    private fun getTitle(): String? {
        return when(viewModel.args.type){
            SeeAllType.TOP_RATED_TV -> SeeAllType.TOP_RATED_TV.value
            SeeAllType.POPULAR_TV -> SeeAllType.POPULAR_TV.value
            SeeAllType.LATEST_TV -> SeeAllType.LATEST_TV.value
            SeeAllType.ON_THE_AIR_TV -> SeeAllType.ON_THE_AIR_TV.value
            SeeAllType.UPCOMING_MOVIE -> SeeAllType.UPCOMING_MOVIE.value
            SeeAllType.TRENDING_MOVIE ->  SeeAllType.TRENDING_MOVIE.value
            SeeAllType.NOW_PLAYING_MOVIE -> SeeAllType.NOW_PLAYING_MOVIE.value
            SeeAllType.TOP_RATED_MOVIE -> SeeAllType.TOP_RATED_MOVIE.value
            SeeAllType.MYSTERY_MOVIE -> SeeAllType.MYSTERY_MOVIE.value
            SeeAllType.ADVENTURE_MOVIE -> SeeAllType.ADVENTURE_MOVIE.value
            SeeAllType.ACTOR_MOVIES -> SeeAllType.ACTOR_MOVIES.value
        }
    }
}