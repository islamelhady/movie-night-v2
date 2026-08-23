package com.elhady.movies.feature.details.presentation.episodedetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.elhady.movies.feature.details.BR
import com.elhady.movies.feature.details.R
import com.elhady.movies.feature.details.databinding.ItemEpisodeDetailsRateBottomSheetBinding
import com.elhady.movies.core.ui.base.BaseBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeRateBottomSheet :
    BaseBottomSheet<ItemEpisodeDetailsRateBottomSheetBinding>() {

    override val layoutIdFragment: Int = R.layout.item_episode_details_rate_bottom_sheet
    private var listener: BottomSheetListener? = null

    fun setListener(listener: BottomSheetListener) {
        this.listener = listener
    }

    private var userRating = 0f

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        setupRatingBar()
        setupApplyButton()
    }

    private fun setupRatingBar() {
        binding.episodeRatingBar.setOnRatingBarChangeListener { _, rating, _ ->
            userRating = rating * 2
        }
    }

    private fun setupApplyButton() {
        binding.buttonApply.setOnClickListener {
            listener?.onApplyRateBottomSheet(userRating)
            dismiss()
        }
    }
}
