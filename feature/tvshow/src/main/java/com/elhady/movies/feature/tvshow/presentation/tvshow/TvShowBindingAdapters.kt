package com.elhady.movies.feature.tvshow.presentation.tvshow

import androidx.databinding.BindingAdapter
import com.elhady.movies.feature.tvshow.R
import com.google.android.material.chip.ChipGroup

@BindingAdapter(value = ["app:selectedTvShowType"])
fun ChipGroup.setSelectedTvShowChip(type: TvShowType) {
    when (type) {
        TvShowType.ON_THE_AIR -> check(R.id.chip_on_the_air)
        TvShowType.AIRING_TODAY -> check(R.id.chip_airing_today)
        TvShowType.TOP_RATED -> check(R.id.chip_top_rated)
        TvShowType.POPULAR -> check(R.id.chip_popular)
    }
}
