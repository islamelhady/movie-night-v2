package com.elhady.movies.feature.home.presentation.ui.adapter

import com.elhady.movies.feature.home.BR
import com.elhady.movies.feature.home.R
import com.elhady.movies.core.ui.bases.BaseAdapter
import com.elhady.movies.feature.home.presentation.HomeListener
import com.elhady.movies.feature.home.presentation.NowPlayingUiState


class NowPlayingAdapter(
    list: List<NowPlayingUiState>, listener: HomeListener
) : BaseAdapter<NowPlayingUiState>(list, listener) {
    override val layoutID = R.layout.home_item_now_playing
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
