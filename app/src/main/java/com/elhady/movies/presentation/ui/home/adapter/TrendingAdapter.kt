package com.elhady.movies.presentation.ui.home.adapter

import com.elhady.movies.BR
import com.elhady.movies.R
import com.elhady.movies.core.common.bases.BaseAdapter
import com.elhady.movies.presentation.viewmodel.home.HomeListener
import com.elhady.movies.presentation.viewmodel.home.TrendingMoviesUiState

class TrendingAdapter(
    list: List<TrendingMoviesUiState>,
    listener: HomeListener
) : BaseAdapter<TrendingMoviesUiState>(list, listener) {
    override val layoutID = R.layout.home_item_trending
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
