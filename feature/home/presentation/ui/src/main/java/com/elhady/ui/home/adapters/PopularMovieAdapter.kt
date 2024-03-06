package com.elhady.ui.home.adapters

import com.elhady.base.BaseAdapter
import com.elhady.ui.R
import com.elhady.viewmodel.home.homeUiState.HomeListener
import com.elhady.viewmodel.home.homeUiState.PopularMoviesUiState


class PopularMovieAdapter(items: List<PopularMoviesUiState>, listener: HomeListener) :
    BaseAdapter<PopularMoviesUiState>(items, listener) {
    override val layoutID: Int = R.layout.home_item_popular_movie
}


