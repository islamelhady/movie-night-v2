package com.elhady.movies.feature.watchlist.presentation.listcontents.adapter

import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.feature.watchlist.BR
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.feature.watchlist.presentation.listcontents.MovieUiState
import com.elhady.movies.feature.watchlist.presentation.listcontents.ListContentsAdapterListener


class  ListContentsAdapter(items: List<MovieUiState>, listener: ListContentsAdapterListener):
    BaseAdapter<MovieUiState>(items, listener) {

    override val layoutID = R.layout.item_lists_details
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
