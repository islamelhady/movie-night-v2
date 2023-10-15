package com.elhady.movies.ui.home.adapters

import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.models.PopularUiState

class PopularMovieAdapter(items: List<PopularUiState>, listener: MovieInteractionListener) :
    BaseAdapter<PopularUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_popular_movie
}


