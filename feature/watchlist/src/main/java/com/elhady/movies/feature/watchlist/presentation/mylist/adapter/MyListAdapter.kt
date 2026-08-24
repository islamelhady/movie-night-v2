package com.elhady.movies.feature.watchlist.presentation.mylist.adapter

import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.feature.watchlist.BR
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.feature.watchlist.presentation.mylist.ListMovieUiState
import com.elhady.movies.feature.watchlist.presentation.mylist.MyListAdapterListener

class  MyListAdapter(items: List<ListMovieUiState>, listener: MyListAdapterListener):
    BaseAdapter<ListMovieUiState>(items, listener) {

    override val layoutID = R.layout.item_my_list
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
