package com.elhady.ui.home.adapters

import com.elhady.base.BaseAdapter
import com.elhady.ui.R
import com.elhady.viewmodel.home.homeUiState.HomeListener
import com.elhady.viewmodel.home.homeUiState.OnTheAirTVShowsUiState


class OnTheAirAdapter(items: List<OnTheAirTVShowsUiState>, val listener: HomeListener): BaseAdapter<OnTheAirTVShowsUiState>(items, listener) {
    override val layoutID: Int = R.layout.home_item_on_the_air_tv_show
}