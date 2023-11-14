package com.elhady.movies.ui.seriesDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentSeriesDetailsBinding
import com.elhady.movies.domain.enums.MediaType
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.utilities.EventObserve
import com.elhady.movies.utilities.collectLast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SeriesDetailsFragment : BaseFragment<FragmentSeriesDetailsBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_series_details
    override val viewModel: SeriesDetailsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val seriesAdapter = SeriesDetailsAdapter(mutableListOf(), viewModel)
        binding.recyclerSeriesDetails.adapter = seriesAdapter

        collectEvent()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.seriesUiState.collect {
                seriesAdapter.setItems(viewModel.seriesUiState.value.seriesItems)
            }
        }
    }

    private fun collectEvent() {
        collectLast(viewModel.seriesUiEvent){ event ->
            event?.getContentIfNotHandled()?.let {
                onEvent(it)
            }
        }
    }

    private fun onEvent(event: SeriesDetailsUiEvent){
        when(event){
            is SeriesDetailsUiEvent.ClickSeasonEvent -> findNavController().navigate(SeriesDetailsFragmentDirections.actionTvShowDetailsFragmentToEpisodesFragment(seriesId = viewModel.args.seriesId, event.seasonNumber))
            SeriesDetailsUiEvent.ClickBackButtonEvent -> findNavController().popBackStack()
            is SeriesDetailsUiEvent.ClickCastEvent -> findNavController().navigate(SeriesDetailsFragmentDirections.actionTvShowDetailsFragmentToActorDetailsFragment(event.castId))
            SeriesDetailsUiEvent.ClickPlayTrailerEvent -> findNavController().navigate(SeriesDetailsFragmentDirections.actionTvShowDetailsFragmentToVideoFragment(viewModel.args.seriesId, MediaType.SERIES))
            is SeriesDetailsUiEvent.ClickSimilarSeriesEvent -> findNavController().navigate(SeriesDetailsFragmentDirections.actionTvShowDetailsFragmentToMovieDetailsFragment(event.seriesId))
            SeriesDetailsUiEvent.ClickViewReviews -> findNavController().navigate(SeriesDetailsFragmentDirections.actionTvShowDetailsFragmentToReviewsFragment(mediaId = viewModel.args.seriesId, mediaType = MediaType.SERIES))
        }
    }


}