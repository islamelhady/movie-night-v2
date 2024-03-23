package com.elhady.movies.presentation.ui.tv_shows

import androidx.databinding.BindingAdapter
import com.elhady.movies.R
import com.elhady.movies.presentation.viewmodel.tv_shows.TVShowsType
import com.google.android.material.chip.ChipGroup

@BindingAdapter(value = ["app:selectedTVShowType"])
fun ChipGroup.setSelectedTVShowsChip(type: TVShowsType) {
    when (type) {
        TVShowsType.ON_THE_AIR -> check(R.id.chip_on_the_air)
        TVShowsType.AIRING_TODAY -> check(R.id.chip_airing_today)
        TVShowsType.TOP_RATED -> check(R.id.chip_top_rated)
        TVShowsType.POPULAR -> check(R.id.chip_popular)
    }
}