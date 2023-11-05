package com.elhady.movies.ui.explore

import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener

class TrendingAdapter(items: List<TrendingMediaUiState>, listener: TrendingInteractionListener) :
    BaseAdapter<TrendingMediaUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_trending
}

interface TrendingInteractionListener : BaseInteractionListener {
    fun onClickTrending()
}