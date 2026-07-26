package com.elhady.movies.core.ui.adapters

import com.elhady.movies.core.ui.BR
import com.elhady.movies.core.ui.bases.BaseAdapter
import com.elhady.movies.core.ui.listener.MovieListener
import com.elhady.movies.core.ui.model.MovieHorizontalUIState

class MovieHorizontalAdapter(
    list: List<MovieHorizontalUIState>,
    listener: MovieListener
) : BaseAdapter<MovieHorizontalUIState>(list, listener) {
    override val layoutID = com.elhady.movies.core.ui.R.layout.item_movie_horizontal
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
