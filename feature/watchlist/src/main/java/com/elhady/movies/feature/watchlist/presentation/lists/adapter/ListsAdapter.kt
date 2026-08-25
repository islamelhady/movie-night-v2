package com.elhady.movies.feature.watchlist.presentation.lists.adapter

import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.feature.watchlist.BR
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.feature.watchlist.presentation.lists.ListMovieUiState
import com.elhady.movies.feature.watchlist.presentation.lists.ListsAdapterListener

class  ListsAdapter(items: List<ListMovieUiState>, listener: ListsAdapterListener):
    BaseAdapter<ListMovieUiState>(items, listener) {

    override val layoutID = R.layout.item_list
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
