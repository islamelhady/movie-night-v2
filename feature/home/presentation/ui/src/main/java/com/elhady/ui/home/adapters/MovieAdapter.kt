package com.elhady.ui.home.adapters

import com.elhady.base.BaseAdapter
import com.elhady.ui.R
import com.elhady.viewmodel.home.homeUiState.HomeListener
import com.elhady.viewmodel.home.homeUiState.MoviesUiState


class MovieAdapter(items: List<MoviesUiState>, val listener: HomeListener) :
    BaseAdapter<MoviesUiState>(items, listener) {
    override val layoutID: Int = R.layout.home_item_movie
}