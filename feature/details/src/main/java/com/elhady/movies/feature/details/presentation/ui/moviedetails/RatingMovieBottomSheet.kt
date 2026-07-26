package com.elhady.movies.feature.details.presentation.ui.moviedetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.elhady.movies.feature.details.BR
import com.elhady.movies.feature.details.R
import com.elhady.movies.core.ui.R as CoreUiR
import com.elhady.movies.feature.details.databinding.MovieRatingBottomSheetBinding
import com.elhady.movies.core.ui.bases.BaseBottomSheet
import com.elhady.movies.feature.details.presentation.ui.tvdetails.BottomSheetDismissListener
import com.elhady.movies.feature.details.presentation.moviedetails.MovieDetailsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RatingMovieBottomSheet :
    BaseBottomSheet<MovieRatingBottomSheetBinding>() {
    override val layoutIdFragment: Int = R.layout.movie_rating_bottom_sheet
    override val viewModel by activityViewModels<MovieDetailsViewModel>()
    override val viewModelVariableId: Int = BR.viewModel

    private var dismissListener: BottomSheetDismissListener? = null

    fun setListener(dismissListener: BottomSheetDismissListener) {
        this.dismissListener = dismissListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var userRating = 0f

        binding.movieRatingBar.setOnRatingBarChangeListener { _, rating, _ ->
            userRating = rating * 2
        }
        binding.buttonApply.setOnClickListener {
            if (userRating == 0f) {
                showSnackBar(getString(CoreUiR.string.please_rate_first))
            } else {
                dismissListener?.onApplyRateBottomSheet()
                dismissListener?.updateRatingValue(userRating)
                dismiss()
            }
        }
        binding.movieRatingBar.rating = dismissListener?.getUserRating() ?: 0f
    }

    private fun showSnackBar(messages: String) {
        Snackbar.make(binding.root, messages, Snackbar.LENGTH_SHORT).show()
    }
}

