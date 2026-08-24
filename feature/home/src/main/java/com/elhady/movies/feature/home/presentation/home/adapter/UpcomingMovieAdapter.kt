package com.elhady.movies.feature.home.presentation.home.adapter

import com.elhady.movies.feature.home.BR
import com.elhady.movies.feature.home.R
import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.feature.home.presentation.home.HomeAdapterListener
import com.elhady.movies.feature.home.presentation.home.UpcomingMovieUiState

class UpcomingMovieAdapter(
    upcomingItems: List<UpcomingMovieUiState>,
    listener: HomeAdapterListener
) : BaseAdapter<UpcomingMovieUiState>(
    upcomingItems, listener
) {
    override val layoutID = R.layout.home_item_image_slider
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
