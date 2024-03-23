package com.elhady.movies.presentation.ui.episode_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.ItemEpisodeDetailsRateBottomSheetBinding
import com.elhady.movies.core.bases.BaseBottomSheet
import com.elhady.movies.presentation.viewmodel.episodedetails.EpisodeDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeRateBottomSheet : BaseBottomSheet<ItemEpisodeDetailsRateBottomSheetBinding>() {
    override val layoutIdFragment: Int = R.layout.item_episode_details_rate_bottom_sheet
    override val viewModel by activityViewModels<EpisodeDetailsViewModel>()
    private var dismissListener: BottomSheetListener? = null

    fun setListener(dismissListener: BottomSheetListener) {
        this.dismissListener = dismissListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        var userRating = 0f

        binding.episodeRatingBar.setOnRatingBarChangeListener { _, rating, _ ->
            userRating = rating * 2
        }
        binding.buttonApply.setOnClickListener {
            dismissListener?.onApplyRateBottomSheet()
            dismissListener?.updateRatingValue(userRating)
            dismiss()
        }
    }
}


interface BottomSheetListener {
    fun onApplyRateBottomSheet()
    fun updateRatingValue(rate: Float)
}