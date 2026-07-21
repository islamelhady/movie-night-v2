package com.elhady.movies.presentation.ui.common.adapters

import com.elhady.movies.BR
import com.elhady.movies.R
import com.elhady.movies.core.common.bases.BaseAdapter
import com.elhady.movies.presentation.viewmodel.common.listener.MovieListener
import com.elhady.movies.presentation.viewmodel.common.model.MovieHorizontalUIState

class MovieHorizontalAdapter(
    list: List<MovieHorizontalUIState>,
    listener: MovieListener
) : BaseAdapter<MovieHorizontalUIState>(list, listener) {
    override val layoutID = R.layout.item_movie_horizontal
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
