package com.elhady.movies.feature.home.presentation.home.adapter

import com.elhady.movies.feature.home.BR
import com.elhady.movies.feature.home.R
import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.feature.home.presentation.home.HomeAdapterListener
import com.elhady.movies.feature.home.presentation.home.NowPlayingMovieUiState


class NowPlayingMovieAdapter(
    nowPlayingItems: List<NowPlayingMovieUiState>, listener: HomeAdapterListener
) : BaseAdapter<NowPlayingMovieUiState>(nowPlayingItems, listener) {
    override val layoutID = R.layout.home_item_now_playing
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
