package com.elhady.movies.feature.home.presentation.home.adapter

import com.elhady.movies.feature.home.BR
import com.elhady.movies.feature.home.R
import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.core.ui.interaction.MovieListener
import com.elhady.movies.feature.home.presentation.home.HomeListener
import com.elhady.movies.feature.home.presentation.home.UpcomingMovieUiState

class UpcomingMovieAdapter(
    upComingList: List<UpcomingMovieUiState>, listener: MovieListener
) : BaseAdapter<UpcomingMovieUiState>(
    upComingList, listener
) {
    override val layoutID = R.layout.home_item_image_slider
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
