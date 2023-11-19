package com.elhady.movies.ui.movieDetails.saveMovie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.BottomSheetSaveMovieBinding
import com.elhady.movies.ui.base.BaseFragmentBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveMovieBottomSheet : BaseFragmentBottomSheet<BottomSheetSaveMovieBinding>() {

    override val layoutIdFragment: Int = R.layout.bottom_sheet_save_movie
    override val viewModel: SaveMovieViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
    }

    private fun setupAdapter() {
        binding.recyclerSaveMovie.adapter = SaveListAdapter(mutableListOf(), viewModel)
    }


}