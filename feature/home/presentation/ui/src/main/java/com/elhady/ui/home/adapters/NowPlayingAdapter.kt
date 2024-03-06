package com.elhady.ui.home.adapters

import com.elhady.base.BaseAdapter
import com.elhady.ui.R
import com.elhady.viewmodel.home.homeUiState.HomeListener
import com.elhady.viewmodel.home.homeUiState.NowPlayingMoviesUiState


class NowPlayingAdapter(list: List<NowPlayingMoviesUiState>, listener: HomeListener
) : BaseAdapter<NowPlayingMoviesUiState>(list, listener) {
    override val layoutID = R.layout.home_item_now_playing
}