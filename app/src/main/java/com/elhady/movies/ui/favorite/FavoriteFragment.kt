package com.elhady.movies.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentFavoriteBinding
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.utilities.collectLast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavCreatedListUiState, FavouriteUiEvent>() {

    override val layoutIdFragment: Int = R.layout.fragment_favorite
    override val viewModel: FavoriteViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(true)
        setupAdapter()
    }

    private fun setupAdapter(){
        binding.recyclerFavourite.adapter = CreatedListAdapter(mutableListOf(), viewModel)
    }

    override fun onEvent(event: FavouriteUiEvent) {
        when(event){
            FavouriteUiEvent.CLickAddEvent -> findNavController().navigate(FavoriteFragmentDirections.actionFavoriteFragmentToCreateListDialog())
            is FavouriteUiEvent.ClickSelectedItemEvent -> findNavController().navigate(FavoriteFragmentDirections.actionFavoriteFragmentToFavListDetailsFragment(mediaId = event.id, listName = event.listName))
            FavouriteUiEvent.ClickLogin -> findNavController().navigate(FavoriteFragmentDirections.actionFavoriteFragmentToLoginFragment())
            FavouriteUiEvent.ClickCreateEvent -> TODO()
        }

    }


}