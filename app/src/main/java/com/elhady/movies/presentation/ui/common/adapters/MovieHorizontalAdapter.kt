package com.elhady.movies.presentation.ui.common.adapters

import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseAdapter
import com.elhady.movies.presentation.viewmodel.common.listener.MovieListener
import com.elhady.movies.presentation.viewmodel.common.model.MovieHorizontalUIState

class MovieHorizontalAdapter(
    list: List<MovieHorizontalUIState>,
    listener: MovieListener
) : BaseAdapter<MovieHorizontalUIState>(list, listener) {
    override val layoutID = R.layout.item_movie_horizontal
}