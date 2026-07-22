package com.elhady.movies.feature.home.presentation.ui.adapter

import com.elhady.movies.feature.home.BR
import com.elhady.movies.feature.home.R
import com.elhady.movies.core.common.bases.BaseAdapter
import com.elhady.movies.feature.home.presentation.HomeListener
import com.elhady.movies.feature.home.presentation.TrendingMoviesUiState

class TrendingAdapter(
    list: List<TrendingMoviesUiState>,
    listener: HomeListener
) : BaseAdapter<TrendingMoviesUiState>(list, listener) {
    override val layoutID = R.layout.home_item_trending
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
