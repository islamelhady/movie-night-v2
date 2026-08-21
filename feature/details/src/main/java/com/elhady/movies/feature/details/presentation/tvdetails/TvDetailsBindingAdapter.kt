package com.elhady.movies.feature.details.presentation.tvdetails

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.elhady.movies.feature.details.R
import com.elhady.movies.core.ui.R as CoreUiR
import com.elhady.movies.core.ui.databinding.GenreChipBinding
import com.elhady.movies.core.ui.interaction.ChipListener
import com.elhady.movies.core.ui.state.UserListUiState
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup

@BindingAdapter("app:chips")
fun ChipGroup.setChips(chips: List<String>) {
    removeAllViews()
    val inflater = LayoutInflater.from(context)
    for (chipText in chips) {
        val chip = inflater.inflate(R.layout.tv_details_item_chip, this, false) as Chip
        val chipDrawable = ChipDrawable.createFromAttributes(
            this.context,
            null,
            0,
            CoreUiR.style.MediaDetailsChipStyle
        )
        chip.setChipDrawable(chipDrawable)
        chip.apply {
            text = chipText
            isEnabled = false
        }
        addView(chip)
    }
}

