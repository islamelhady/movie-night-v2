package com.elhady.movies.feature.details.presentation.tvdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import com.elhady.movies.feature.details.R
import com.elhady.movies.core.ui.R as CoreUiR
import com.elhady.movies.core.ui.base.BaseBottomSheet
import com.elhady.movies.feature.details.databinding.TvDetailsItemBotomSheetBinding
import com.elhady.movies.feature.details.presentation.tvdetails.listener.BottomSheetDismissListener
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RateTvDetailsBottomSheet :
    BaseBottomSheet<TvDetailsItemBotomSheetBinding>() {
    override val layoutIdFragment: Int = R.layout.tv_details_item_botom_sheet
    override val viewModel: TvDetailsViewModel by activityViewModels()
    override val viewModelVariableId: Int = 0

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
                showSnackBar(getString(CoreUiR.string.please_rate_first))
            } else {
                dismissListener?.updateRatingValue(userRating)
                dismissListener?.onApplyRateBottomSheet()
                dismiss()
            }
        }
        binding.tvRatingBar.rating = dismissListener?.getUserRating() ?: 0f
    }

    private fun showSnackBar(messages: String) {
        Snackbar.make(binding.root, messages, Snackbar.LENGTH_SHORT).show()
    }
}

class GenericViewModel : ViewModel()
