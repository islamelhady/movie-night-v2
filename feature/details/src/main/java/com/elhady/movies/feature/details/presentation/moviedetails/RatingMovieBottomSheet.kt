package com.elhady.movies.feature.details.presentation.moviedetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.feature.details.R
import com.elhady.movies.feature.details.databinding.MovieRatingBottomSheetBinding
import com.elhady.movies.core.ui.base.BaseBottomSheet
import com.elhady.movies.feature.details.presentation.tvdetails.listener.BottomSheetDismissListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RatingMovieBottomSheet :
    BaseBottomSheet<MovieRatingBottomSheetBinding>() {
    override val layoutIdFragment: Int = R.layout.movie_rating_bottom_sheet
    override val viewModel by viewModels<MovieDetailsViewModel>({ requireParentFragment() })
    private var dismissListener: BottomSheetDismissListener? = null

    fun setListener(dismissListener: BottomSheetDismissListener) {
        this.dismissListener = dismissListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        collectFlow(viewModel.state) {
            binding.state = it
        }

        binding.movieRatingBar.setOnRatingBarChangeListener { _, rating, _ ->
            dismissListener?.updateRatingValue(rating * 2)
        }

        binding.buttonApply.setOnClickListener {
            dismissListener?.onApplyRateBottomSheet()
            dismiss()
        }
    }
}
