package com.elhady.movies.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentFavoriteBinding
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.utilities.collectLast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    override val layoutIdFragment: Int = R.layout.fragment_favorite
    override val viewModel: FavoriteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectEvent()
    }

    private fun collectEvent() {
        collectLast(viewModel.uiEvent){ event ->
            event?.getContentIfNotHandled()?.let {
                onEvent(it)
            }
        }
    }

    private fun onEvent(event: FavouriteUiEvent) {
        when(event){
            FavouriteUiEvent.CLickAddEvent -> findNavController().navigate(FavoriteFragmentDirections.actionFavoriteFragmentToCreateListDialog())
            else -> {}
        }

    }


}