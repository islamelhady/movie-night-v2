package com.elhady.movies.feature.home.presentation.ui.adapter

import com.elhady.movies.feature.home.BR
import com.elhady.movies.feature.home.R
import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.feature.home.presentation.AiringTodayTvShowUiState
import com.elhady.movies.feature.home.presentation.HomeListener

class AiringTodayAdapter(
    itemsAiringToday: List<AiringTodayTvShowUiState>,
    listener: HomeListener
) : BaseAdapter<AiringTodayTvShowUiState>(itemsAiringToday, listener) {
    override val layoutID = R.layout.home_item_airing_today
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener

}
