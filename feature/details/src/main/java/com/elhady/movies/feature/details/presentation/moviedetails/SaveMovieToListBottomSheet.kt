package com.elhady.movies.feature.details.presentation.moviedetails

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.elhady.movies.feature.details.R
import com.elhady.movies.core.ui.base.BaseBottomSheet
import com.elhady.movies.core.ui.interaction.ChipListener
import com.elhady.movies.core.ui.util.setGenreChips
import com.elhady.movies.feature.details.databinding.SaveMovieToCreateListBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class SaveMovieToListBottomSheet :
    BaseBottomSheet<SaveMovieToCreateListBottomSheetBinding>(), ChipListener {

    override val layoutIdFragment: Int = R.layout.save_movie_to_create_list_bottom_sheet
    override val viewModel: MovieDetailsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listener = MovieDetailsBottomSheetListener()

        collectFlow(viewModel.effect) { onEffect(it) }

        collectFlow(viewModel.state.map { it.userLists }.distinctUntilChanged()) {
            Log.i("list", "new list => $it")
            binding.chipGroupGenre.setGenreChips(it, this)
        }
        
        collectFlow(viewModel.state) {
            binding.state = it
        }
    }

    private fun onEffect(effect: MovieDetailsUiEffect) {
        when (effect) {
            MovieDetailsUiEffect.AddListToBottomSheet -> {
                binding.groupCreateList.visibility =
                    if (binding.chipAddNewList.isChecked) View.VISIBLE else View.GONE
            }
            MovieDetailsUiEffect.DoneEvent -> dismiss()
            MovieDetailsUiEffect.CloseBottomSheet -> dismiss()
            else -> {}
        }
    }

    override fun onChipClick(id: Int) {
        viewModel.onEvent(MovieDetailsUiEvent.ChipClicked(id))
    }

    inner class MovieDetailsBottomSheetListener {
        fun onDismiss() = viewModel.onEvent(MovieDetailsUiEvent.CloseClicked)
        fun onDone() = viewModel.onEvent(MovieDetailsUiEvent.DoneClicked)
        fun onCreateList(name: String) = viewModel.onEvent(MovieDetailsUiEvent.CreateListClicked(name))
        fun onFavourite() = viewModel.onEvent(MovieDetailsUiEvent.FavouriteClicked)
        fun onWatchlist() = viewModel.onEvent(MovieDetailsUiEvent.WatchlistClicked)
        fun onAddList() = viewModel.onEvent(MovieDetailsUiEvent.AddListClicked)
    }
}
