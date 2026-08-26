package com.elhady.movies.feature.home.presentation.home.adapter

import com.elhady.movies.feature.home.BR
import com.elhady.movies.feature.home.R
import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.feature.home.presentation.home.HomeAdapterListener
import com.elhady.movies.feature.home.presentation.home.TrendingMovieUiState

class TrendingMovieAdapter(
    list: List<TrendingMovieUiState>,
    listener: HomeAdapterListener
) : BaseAdapter<TrendingMovieUiState>(list, listener
) {
    override val layoutID = R.layout.home_item_trending
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
