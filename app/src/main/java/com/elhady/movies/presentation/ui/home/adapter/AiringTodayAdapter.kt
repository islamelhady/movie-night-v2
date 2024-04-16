package com.elhady.movies.presentation.ui.home.adapter

import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseAdapter
import com.elhady.movies.presentation.viewmodel.home.AiringTodayTvShowUiState
import com.elhady.movies.presentation.viewmodel.home.HomeListener

class AiringTodayAdapter(
    itemsAiringToday: List<AiringTodayTvShowUiState>,
    listener: HomeListener
) : BaseAdapter<AiringTodayTvShowUiState>(itemsAiringToday, listener) {
    override val layoutID = R.layout.home_item_airing_today

}