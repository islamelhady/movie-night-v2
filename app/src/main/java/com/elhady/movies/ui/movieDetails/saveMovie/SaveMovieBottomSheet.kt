package com.elhady.movies.ui.movieDetails.saveMovie

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.BottomSheetSaveMovieBinding
import com.elhady.movies.ui.base.BaseFragmentBottomSheet
import com.elhady.movies.utilities.collectLast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveMovieBottomSheet : BaseFragmentBottomSheet<BottomSheetSaveMovieBinding>() {

    override val layoutIdFragment: Int = R.layout.bottom_sheet_save_movie
    override val viewModel: SaveMovieViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        collectEvent()
    }

    private fun setupAdapter() {
        binding.recyclerSaveMovie.adapter = SaveListAdapter(mutableListOf(), viewModel)
    }

    fun collectEvent() {
        collectLast(viewModel.event) {
            onEvent(it)
        }
    }

    private fun onEvent(event: SaveMovieUiEvent) {
        if (event is SaveMovieUiEvent.DisplayMessage) {
            if (!event.message.isNullOrBlank()) {
                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }


}