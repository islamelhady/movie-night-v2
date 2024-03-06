package com.elhady.ui.home.adapters

import com.elhady.base.BaseAdapter
import com.elhady.ui.R
import com.elhady.viewmodel.home.homeUiState.AiringTodayTVShowsUiState
import com.elhady.viewmodel.home.homeUiState.HomeListener


class AiringTodayAdapter(items: List<AiringTodayTVShowsUiState>, listener: HomeListener) :
    BaseAdapter<AiringTodayTVShowsUiState>(items, listener) {
    override val layoutID: Int = R.layout.home_item_airing_today_tv_shows
}