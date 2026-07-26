package com.elhady.movies.feature.watchlist.presentation.ui.mylistdetails

import com.elhady.movies.feature.watchlist.BR
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.core.ui.bases.BaseAdapter
import com.elhady.movies.feature.watchlist.presentation.mylistdetails.MyListDetailsListener
import com.elhady.movies.feature.watchlist.presentation.mylistdetails.MyListDetailsUiState


class  MyListDetailsAdapter(items: List<MyListDetailsUiState>, listener: MyListDetailsListener):
    BaseAdapter<MyListDetailsUiState>(items, listener) {

    override val layoutID = R.layout.item_my_list_details
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
