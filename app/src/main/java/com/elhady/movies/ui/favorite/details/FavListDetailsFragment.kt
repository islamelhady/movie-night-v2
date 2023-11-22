package com.elhady.movies.ui.favorite.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentFavListDetailsBinding
import com.elhady.movies.domain.enums.MediaType
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.ui.favorite.FavoriteFragmentDirections
import com.elhady.movies.utilities.collectLast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavListDetailsFragment : BaseFragment<FragmentFavListDetailsBinding>() {

    override val layoutIdFragment: Int = R.layout.fragment_fav_list_details
    override val viewModel: FavListDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        collectEvent()
    }

    private fun setupAdapter() {
        binding.recyclerListDetails.adapter = ListDetailsAdapter(mutableListOf(), viewModel)
    }

    private fun collectEvent() {
        collectLast(viewModel.event) {
            onEvent(it)
        }
    }

    private fun onEvent(event: ListDetailsUiEvent) {
        if (event is ListDetailsUiEvent.OnItemSelected) {
            if (event.savedMediaUiState.mediaType == MediaType.MOVIES.value) {
                navigateToMovieDetails(event.savedMediaUiState.mediaID)
            } else {
                navigateToTvShowDetails(event.savedMediaUiState.mediaID)
            }
        }
    }

    private fun navigateToMovieDetails(id: Int) {
        findNavController().navigate(
            FavListDetailsFragmentDirections.actionFavListDetailsFragmentToMovieDetailsFragment(id)
        )
    }

    private fun navigateToTvShowDetails(id: Int) {
        findNavController().navigate(
            FavListDetailsFragmentDirections.actionFavListDetailsFragmentToTvShowDetailsFragment(id)
        )
    }
}