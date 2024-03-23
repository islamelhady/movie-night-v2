package com.elhady.movies.presentation.ui.tvdetails

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.elhady.movies.R
import com.elhady.movies.databinding.GenereChipBinding
import com.elhady.movies.presentation.viewmodel.common.listener.ChipListener
import com.elhady.movies.presentation.viewmodel.common.model.UserListUi
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
            R.style.MediaDetailsChipStyle
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
            R.layout.genere_chip,
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