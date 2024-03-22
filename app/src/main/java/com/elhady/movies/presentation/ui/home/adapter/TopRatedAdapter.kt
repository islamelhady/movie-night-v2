package com.elhady.movies.presentation.ui.home.adapter

import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseAdapter
import com.elhady.movies.presentation.viewmodel.home.HomeListener
import com.elhady.movies.presentation.viewmodel.home.TopRatedUiState

class TopRatedAdapter(
    itemsTopRated: List<TopRatedUiState>,
    listener: HomeListener
) : BaseAdapter<TopRatedUiState>(itemsTopRated, listener) {
    override val layoutID = R.layout.home_item_top_rated

}