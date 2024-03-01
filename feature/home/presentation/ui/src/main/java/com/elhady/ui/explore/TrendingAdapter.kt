package com.elhady.ui.explore

import com.elhady.base.BaseAdapter
import com.elhady.base.BaseInteractionListener
import com.elhady.ui.R
import com.elhady.viewmodel.explore.TrendingMediaUiState


class TrendingAdapter(items: List<TrendingMediaUiState>, listener: TrendingInteractionListener) :
    BaseAdapter<TrendingMediaUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_trending
}

interface TrendingInteractionListener : BaseInteractionListener {
    fun onClickTrending(item: TrendingMediaUiState)
}