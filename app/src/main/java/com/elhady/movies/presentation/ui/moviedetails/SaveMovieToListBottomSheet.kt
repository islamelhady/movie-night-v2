package com.elhady.movies.presentation.ui.moviedetails

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseBottomSheet
import com.elhady.movies.databinding.SaveMovieToCreateListBottomSheetBinding
import com.elhady.movies.presentation.viewmodel.moviedetails.MovieDetailsUiEvent
import com.elhady.movies.presentation.viewmodel.moviedetails.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SaveMovieToListBottomSheet() :
    BaseBottomSheet<SaveMovieToCreateListBottomSheetBinding>() {

    override val layoutIdFragment: Int = R.layout.save_movie_to_create_list_bottom_sheet
    override val viewModel: MovieDetailsViewModel by activityViewModels()
//    private lateinit var binding: SaveToCreateListBottomSheetBinding
//    val viewModel by activityViewModels<MovieDetailsViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectLatest {
            viewModel.event.collectLatest { onEvent(it) }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.map {
                it.userLists + it.id }.distinctUntilChanged().collectLatest {
                Log.i("list", "new list => ${viewModel.state.value.userLists}")
                binding.chipGroupGenere.setGenreChips(viewModel.state.value.userLists, viewModel)
//                viewModel.emptyUserLists()
                viewModel.getUserLists()

            }
        }

    }

    fun onEvent(event: MovieDetailsUiEvent) {
        when (event) {

            MovieDetailsUiEvent.AddListEvent -> {
                binding.groupCreateList.visibility =
                    if (binding.chipAddNewList.isChecked) View.VISIBLE else View.GONE
            }

            MovieDetailsUiEvent.DoneEvent -> {
//                binding.textViewDone.setOnClickListener {
//                    if (binding.chipFavourite.isChecked) viewModel.onClickFavourite()
//                    if (binding.chipWatchlist.isChecked) viewModel.onClickWatchlist()
//                    viewModel.onDone(viewModel.state.value.userSelectedLists)
                Log.d(
                    "DONE EVENT",
                    "${viewModel.state.value.userSelectedLists}"
                )
                dismiss()
            }


            MovieDetailsUiEvent.CloseEvent -> dismiss()

//            MovieDetailsUiEvent.CreateListEvent -> {
//                binding.materialButtonCreate.setOnClickListener {
//                    viewModel.createUserNewList(binding.textInputEditTextListName.text.toString())
//                    binding.chipGroupGenere.removeViewsInLayout(
//                        0,
//                        binding.chipGroupGenere.childCount - 3
//                    )
//                    binding.groupCreateList.visibility = View.GONE
//                    binding.chipAddNewList.isChecked = false
//                    binding.textInputEditTextListName.setText("")
//                }
//            }

            else -> {}

        }
    }
}

