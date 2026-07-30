package com.elhady.movies.feature.home.presentation.ui.adapter

import com.elhady.movies.feature.home.BR
import com.elhady.movies.feature.home.R
import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.feature.home.presentation.HomeListener
import com.elhady.movies.feature.home.presentation.UpComingMoviesUiState

class UpComingAdapter(
    upComingList: List<UpComingMoviesUiState>, listener: HomeListener
) : BaseAdapter<UpComingMoviesUiState>(upComingList,listener) {
    override val layoutID = R.layout.home_item_image_slider
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
