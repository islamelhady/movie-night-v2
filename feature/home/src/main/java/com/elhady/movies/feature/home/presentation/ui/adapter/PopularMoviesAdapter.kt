package com.elhady.movies.feature.home.presentation.ui.adapter

import com.elhady.movies.feature.home.BR
import com.elhady.movies.feature.home.R
import com.elhady.movies.core.ui.bases.BaseAdapter
import com.elhady.movies.feature.home.presentation.HomeListener
import com.elhady.movies.feature.home.presentation.PopularMoviesUiState

class PopularMoviesAdapter(
    itemsPopular: List<PopularMoviesUiState>,
    listener: HomeListener
) : BaseAdapter<PopularMoviesUiState>(itemsPopular, listener) {
    override val layoutID = R.layout.home_item_popular_movies
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener

}





