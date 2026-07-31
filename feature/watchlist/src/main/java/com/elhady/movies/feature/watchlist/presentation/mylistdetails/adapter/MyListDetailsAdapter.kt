package com.elhady.movies.feature.watchlist.presentation.mylistdetails.adapter

import com.elhady.movies.feature.watchlist.BR
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.feature.watchlist.presentation.mylistdetails.MyListDetailsListener
import com.elhady.movies.feature.watchlist.presentation.mylistdetails.MovieUiState


class  MyListDetailsAdapter(items: List<MovieUiState>, listener: MyListDetailsListener):
    BaseAdapter<MovieUiState>(items, listener) {

    override val layoutID = R.layout.item_my_list_details
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
