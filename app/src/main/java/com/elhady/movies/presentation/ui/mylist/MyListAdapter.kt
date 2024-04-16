package com.elhady.movies.presentation.ui.mylist

import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseAdapter
import com.elhady.movies.presentation.viewmodel.mylist.MyListListener
import com.elhady.movies.presentation.viewmodel.mylist.MyListUiState

class  MyListAdapter(items: List<MyListUiState>, listener: MyListListener):
    BaseAdapter<MyListUiState>(items, listener) {

    override val layoutID = R.layout.item_my_list
}