package com.elhady.movies.presentation.ui.tvdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseBottomSheet
import com.elhady.movies.databinding.TvDetailsItemBotomSheetBinding
import com.elhady.movies.presentation.viewmodel.tvdetails.TvDetailsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RateTvDetailsBottomSheet :
    BaseBottomSheet<TvDetailsItemBotomSheetBinding>() {
    override val layoutIdFragment: Int = R.layout.tv_details_item_botom_sheet
    override val viewModel by activityViewModels<TvDetailsViewModel>()

    private var dismissListener: BottomSheetDismissListener? = null

    fun setListener(dismissListener: BottomSheetDismissListener) {
        this.dismissListener = dismissListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var userRating = 0f

        binding.tvRatingBar.setOnRatingBarChangeListener { _, rating, _ ->
            userRating = rating * 2
        }
        binding.buttonApply.setOnClickListener {
            if (userRating == 0f) {
                showSnackBar(getString(R.string.please_rate_first))
            } else {
                dismissListener?.onApplyRateBottomSheet()
                dismissListener?.updateRatingValue(userRating)
                dismiss()
            }
        }
        binding.tvRatingBar.rating = dismissListener?.getUserRating() ?: 0f
    }

    private fun showSnackBar(messages: String) {
        Snackbar.make(binding.root, messages, Snackbar.LENGTH_SHORT).show()
    }
}

interface BottomSheetDismissListener {
    fun onApplyRateBottomSheet()
    fun updateRatingValue(rate: Float)
    fun getUserRating(): Float
}