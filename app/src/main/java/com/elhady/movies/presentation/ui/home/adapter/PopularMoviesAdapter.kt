package com.elhady.movies.presentation.ui.home.adapter

import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseAdapter
import com.elhady.movies.presentation.viewmodel.home.HomeListener
import com.elhady.movies.presentation.viewmodel.home.PopularMoviesUiState

class PopularMoviesAdapter(
    itemsPopular: List<PopularMoviesUiState>,
    listener: HomeListener
) : BaseAdapter<PopularMoviesUiState>(itemsPopular, listener) {
    override val layoutID = R.layout.home_item_popular_movies

}





