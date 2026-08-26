package com.elhady.movies.feature.tvshow.presentation.tvshow

import androidx.recyclerview.widget.GridLayoutManager
import com.elhady.movies.core.ui.adapter.BaseFooterAdapter
import com.elhady.movies.feature.tvshow.presentation.tvshow.adapter.TvShowAdapter

fun GridLayoutManager.setSpanSize(
    footerAdapter: BaseFooterAdapter,
    adapter: TvShowAdapter,
    spanCount: Int
) {
    this.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if ((position == adapter.itemCount) && footerAdapter.itemCount > 0) {
                spanCount
            } else {
                1
            }
        }
    }
}
