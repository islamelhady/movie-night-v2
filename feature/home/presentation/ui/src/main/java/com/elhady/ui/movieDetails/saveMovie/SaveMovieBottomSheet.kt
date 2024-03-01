package com.elhady.ui.movieDetails.saveMovie

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.elhady.base.BaseFragmentBottomSheet
import com.elhady.ui.R
import com.elhady.ui.databinding.BottomSheetSaveMovieBinding
import com.elhady.viewmodel.movieDetails.saveMovie.SaveMovieUiEvent
import com.elhady.viewmodel.movieDetails.saveMovie.SaveMovieViewModel
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