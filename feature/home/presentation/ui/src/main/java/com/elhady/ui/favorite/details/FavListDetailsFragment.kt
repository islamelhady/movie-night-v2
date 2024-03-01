package com.elhady.ui.favorite.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elhady.base.BaseFragment
import com.elhady.ui.R
import com.elhady.ui.databinding.FragmentFavListDetailsBinding
import com.elhady.viewmodel.favorite.details.FavListDetailsViewModel
import com.elhady.viewmodel.favorite.details.ListDetailsUIState
import com.elhady.viewmodel.favorite.details.ListDetailsUiEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavListDetailsFragment : BaseFragment<FragmentFavListDetailsBinding, ListDetailsUIState, ListDetailsUiEvent>() {

    override val layoutIdFragment: Int = R.layout.fragment_fav_list_details
    override val viewModel: FavListDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(visibility = true, title = viewModel.args.listName)
        setupAdapter()
    }

    private fun setupAdapter() {
        binding.recyclerListDetails.adapter = ListDetailsAdapter(mutableListOf(), viewModel)
    }

    override fun onEvent(event: ListDetailsUiEvent) {
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