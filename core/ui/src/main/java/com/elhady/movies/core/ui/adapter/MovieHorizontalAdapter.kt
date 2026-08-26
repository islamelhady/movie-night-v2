package com.elhady.movies.core.ui.adapter

import com.elhady.movies.core.ui.BR
import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.core.ui.interaction.MovieAdapterListener
import com.elhady.movies.core.ui.state.MovieHorizontalUiState

class MovieHorizontalAdapter(
    list: List<MovieHorizontalUiState>,
    listener: MovieAdapterListener
) : BaseAdapter<MovieHorizontalUiState>(list, listener) {
    override val layoutID = com.elhady.movies.core.ui.R.layout.item_movie_horizontal
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
