package com.elhady.movies.feature.watchlist.presentation.ui.mylist

import com.elhady.movies.feature.watchlist.BR
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.core.common.bases.BaseAdapter
import com.elhady.movies.feature.watchlist.presentation.mylist.MyListListener
import com.elhady.movies.feature.watchlist.presentation.mylist.MyListUiState

class  MyListAdapter(items: List<MyListUiState>, listener: MyListListener):
    BaseAdapter<MyListUiState>(items, listener) {

    override val layoutID = R.layout.item_my_list
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
