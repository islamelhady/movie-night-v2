package com.elhady.movies.presentation.ui.home.adapter

import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseAdapter
import com.elhady.movies.presentation.viewmodel.home.HomeListener
import com.elhady.movies.presentation.viewmodel.home.NowPlayingUiState


class NowPlayingAdapter(
    list: List<NowPlayingUiState>, listener: HomeListener
) : BaseAdapter<NowPlayingUiState>(list, listener) {
    override val layoutID = R.layout.home_item_now_playing
}