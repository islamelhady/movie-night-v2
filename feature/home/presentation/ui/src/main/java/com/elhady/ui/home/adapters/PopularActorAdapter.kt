package com.elhady.ui.home.adapters

import com.elhady.base.BaseAdapter
import com.elhady.ui.R
import com.elhady.viewmodel.home.homeUiState.HomeListener
import com.elhady.viewmodel.home.homeUiState.PopularActorUiState


class PopularActorAdapter(
    items: List<PopularActorUiState>,
    val listener: HomeListener
) : BaseAdapter<PopularActorUiState>(items, listener) {
    override val layoutID: Int = R.layout.home_item_popular_actor
}