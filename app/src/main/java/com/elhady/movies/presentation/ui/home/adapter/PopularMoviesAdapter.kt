package com.elhady.movies.presentation.ui.home.adapter

import com.elhady.movies.BR
import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseAdapter
import com.elhady.movies.presentation.viewmodel.home.HomeListener
import com.elhady.movies.presentation.viewmodel.home.PopularMoviesUiState

class PopularMoviesAdapter(
    itemsPopular: List<PopularMoviesUiState>,
    listener: HomeListener
) : BaseAdapter<PopularMoviesUiState>(itemsPopular, listener) {
    override val layoutID = R.layout.home_item_popular_movies
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener

}





