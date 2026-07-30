package com.elhady.movies.feature.details.presentation.ui.tvdetails

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.elhady.movies.feature.details.R
import com.elhady.movies.core.ui.R as CoreUiR
import com.elhady.movies.core.ui.databinding.GenereChipBinding
import com.elhady.movies.core.ui.listener.ChipListener
import com.elhady.movies.core.ui.state.UserListUi
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

@BindingAdapter(value = ["app:genreChips","app:listener"])
fun ChipGroup.setGenreChips(chips: List<UserListUi>, chipListener: ChipListener) {
    val inflater = LayoutInflater.from(context)
    for (chipUiState in chips) {
        val binding = DataBindingUtil.inflate<GenereChipBinding>(
            inflater,
            com.elhady.movies.core.ui.R.layout.genere_chip,
            this,
            false
        )
        binding.item = chipUiState
        binding.listener = chipListener
        addView(binding.root, 0)
    }
}
@BindingAdapter(value=["app:emptyList"])
fun View.emptyList(list: List<Any>): Int {
    return if (list.isEmpty()) View.GONE else View.VISIBLE
}
