package com.elhady.movies.ui.allMedia

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentAlMediaBinding
import com.elhady.movies.ui.adapter.LoadAdapter
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.ui.models.MediaUiState
import com.elhady.movies.utilities.collect
import com.elhady.movies.utilities.collectLast
import com.elhady.movies.utilities.setSpanSize
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllMediaFragment : BaseFragment<FragmentAlMediaBinding>() {

    override val layoutIdFragment: Int = R.layout.fragment_al_media
    override val viewModel: AllMediaViewModel by viewModels()
    private val allMediaAdapter by lazy {
        AllMediaAdapter(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        collectEvent()
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

    private fun collectEvent() {
        collectLast(viewModel.uiEvent) { event ->
            event?.getContentIfNotHandled()?.let {
                onEvent(it)
            }
        }
    }

    private fun onEvent(event: AllMediaUiEvent) {
        when (event) {
            is AllMediaUiEvent.ClickMovieEvent -> {
                findNavController().navigate(
                    AllMediaFragmentDirections.actionAllMediaFragmentToMovieDetailsFragment(
                        event.mediaId
                    )
                )
            }
            is AllMediaUiEvent.ClickSeriesEvent -> {
                findNavController().navigate(AllMediaFragmentDirections.actionAllMediaFragmentToTvShowDetailsFragment(event.mediaId))

            }
        }
    }
}