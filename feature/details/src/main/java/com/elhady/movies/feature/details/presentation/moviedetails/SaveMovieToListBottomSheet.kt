package com.elhady.movies.feature.details.presentation.moviedetails

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.elhady.movies.feature.details.BR
import com.elhady.movies.feature.details.R
import com.elhady.movies.core.ui.base.BaseBottomSheet
import com.elhady.movies.feature.details.databinding.SaveMovieToCreateListBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class SaveMovieToListBottomSheet :
    BaseBottomSheet<SaveMovieToCreateListBottomSheetBinding>() {

    override val layoutIdFragment: Int = R.layout.save_movie_to_create_list_bottom_sheet
    override val viewModel: MovieDetailsViewModel by activityViewModels()
    override val viewModelVariableId: Int = BR.viewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectFlow(viewModel.effect) { onEvent(it) }

        collectFlow(viewModel.state.map { it.userLists + it.id }.distinctUntilChanged()) {
            Log.i("list", "new list => ${viewModel.state.value.userLists}")
            binding.chipGroupGenre.setGenreChips(viewModel.state.value.userLists, viewModel)
            viewModel.getUserLists()
        }

    }

    private fun onEvent(event: MovieDetailsUiEvent) {
        when (event) {

            MovieDetailsUiEvent.AddListEvent -> {
                binding.groupCreateList.visibility =
                    if (binding.chipAddNewList.isChecked) View.VISIBLE else View.GONE
            }

            MovieDetailsUiEvent.DoneEvent -> {
                Log.d(
                    "DONE EVENT",
                    "${viewModel.state.value.userSelectedLists}"
                )
                dismiss()
            }


            MovieDetailsUiEvent.CloseEvent -> dismiss()

            else -> {}

        }
    }
}
