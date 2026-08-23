package com.elhady.movies.feature.home.presentation.home.adapter

import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.feature.home.BR
import com.elhady.movies.feature.home.R
import com.elhady.movies.feature.home.presentation.home.HomeAdapterListener
import com.elhady.movies.feature.home.presentation.home.PopularMovieUiState

class PopularMoviesAdapter(
    itemsPopular: List<PopularMovieUiState>,
    listener: HomeAdapterListener
) : BaseAdapter<PopularMovieUiState>(itemsPopular, listener) {
    override val layoutID = R.layout.home_item_popular_movies
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener

}





