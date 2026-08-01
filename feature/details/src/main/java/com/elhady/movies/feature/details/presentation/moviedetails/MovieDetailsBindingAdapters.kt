package com.elhady.movies.feature.details.presentation.moviedetails

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.elhady.movies.feature.details.BuildConfig
import com.elhady.movies.core.ui.R as CoreUiR
import com.elhady.movies.core.ui.databinding.GenreChipBinding
import com.elhady.movies.core.ui.interaction.ChipListener
import com.elhady.movies.core.ui.state.UserListUiState
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup

@BindingAdapter(value = ["app:Genres"])
fun ChipGroup.setGenresMovieDetails(
    items: List<String>?,
) {
    this.removeAllViews()
    items?.forEach { genre ->
        val chip = Chip(this.context)
        chip.setCloseIconVisible(true)
        chip.setTextColor(resources.getColor(CoreUiR.color.on_background_38))
        chip.setText(genre)
        chip.ensureAccessibleTouchTarget(0)
        val chipDrawable = ChipDrawable.createFromAttributes(
            this.context,
            null,
            0,
            CoreUiR.style.MediaDetailsChipStyle
        )
        chip.setChipDrawable(chipDrawable)
        chip.isEnabled = false
        this.addView(chip)
    }
}

@BindingAdapter(value = ["app:imageUrlForReviews", "app:autherName"])
fun ImageView.loadImageForReviews(backDropPath: String?, autherName: String) {
    if (!backDropPath.isNullOrEmpty())
        Glide.with(context)
            .load(BuildConfig.IMAGE_BASE_PATH + backDropPath)
            .fitCenter()
            .centerCrop()
            .into(this)
}


@BindingAdapter(value = ["app:genreChips", "app:listener"])
fun ChipGroup.setGenreChips(
    chips: List<UserListUiState>,
    chipListener: ChipListener
) {
    val inflater = LayoutInflater.from(context)
    for (chipUiState in chips) {
        val binding = DataBindingUtil.inflate<GenreChipBinding>(
            inflater,
            com.elhady.movies.core.ui.R.layout.genre_chip,
            this,
            false
        )
        binding.item = chipUiState
        binding.listener = chipListener
        addView(binding.root, 0)
    }
}

@BindingAdapter(value = ["app:emptyList"])
fun View.emptyList(list: List<Any>): Int {
    return if (list.isEmpty()) View.GONE else View.VISIBLE
}
@BindingAdapter(value = ["app:hideOnEmpty"])
fun View.hideOnEmpty(list: List<Any>) {
    if (list.isEmpty()) this.visibility = View.GONE else this.visibility = View.VISIBLE
}
