package com.elhady.movies.presentation.ui.mylistdetails

import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseAdapter
import com.elhady.movies.presentation.viewmodel.mylistdetails.MyListDetailsListener
import com.elhady.movies.presentation.viewmodel.mylistdetails.MyListDetailsUiState


class  MyListDetailsAdapter(items: List<MyListDetailsUiState>, listener: MyListDetailsListener):
    BaseAdapter<MyListDetailsUiState>(items, listener) {

    override val layoutID = R.layout.item_my_list_details
}