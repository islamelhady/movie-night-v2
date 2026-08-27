package com.elhady.movies.feature.explore.presentation.explore

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter(value = ["app:isGridLayout"])
fun RecyclerView.setExploreLayoutManager(isGridLayout: Boolean) {
    val currentLayoutManager = this.layoutManager
    val isCurrentlyGrid = currentLayoutManager is GridLayoutManager
    
    if (isGridLayout != isCurrentlyGrid || currentLayoutManager == null) {
        this.layoutManager = if (isGridLayout) {
            GridLayoutManager(this.context, 2)
        } else {
            LinearLayoutManager(this.context)
        }
    }
}
