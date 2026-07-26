package com.elhady.movies.feature.explore.presentation.explore

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("app:exploreLayoutManagerToggle")
fun RecyclerView.setLayoutManagerToggle(useGrid: Boolean) {
    val layoutManager = if (useGrid) {
        GridLayoutManager(context, 2)
    } else {
        LinearLayoutManager(context)
    }
    this.layoutManager = layoutManager
}
