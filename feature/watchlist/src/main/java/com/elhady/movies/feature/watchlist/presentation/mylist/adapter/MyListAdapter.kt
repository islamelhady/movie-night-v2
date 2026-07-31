package com.elhady.movies.feature.watchlist.presentation.mylist.adapter

import com.elhady.movies.feature.watchlist.BR
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.feature.watchlist.presentation.mylist.MyListListener
import com.elhady.movies.feature.watchlist.presentation.mylist.ListMovieUiState

class  MyListAdapter(items: List<ListMovieUiState>, listener: MyListListener):
    BaseAdapter<ListMovieUiState>(items, listener) {

    override val layoutID = R.layout.item_my_list
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
