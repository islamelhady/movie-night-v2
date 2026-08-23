package com.elhady.movies.feature.home.presentation.home.adapter

import com.elhady.movies.feature.home.BR
import com.elhady.movies.feature.home.R
import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.core.ui.interaction.MovieListener
import com.elhady.movies.feature.home.presentation.home.HomeListener
import com.elhady.movies.feature.home.presentation.home.TopRatedMovieUiState

class TopRatedMovieAdapter(
    itemsTopRated: List<TopRatedMovieUiState>,
    listener: MovieListener
) : BaseAdapter<TopRatedMovieUiState>(itemsTopRated, listener
) {
    override val layoutID = R.layout.home_item_top_rated
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener

}
