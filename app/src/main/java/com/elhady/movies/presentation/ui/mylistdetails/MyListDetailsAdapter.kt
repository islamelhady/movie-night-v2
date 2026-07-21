package com.elhady.movies.presentation.ui.mylistdetails

import com.elhady.movies.BR
import com.elhady.movies.R
import com.elhady.movies.core.common.bases.BaseAdapter
import com.elhady.movies.presentation.viewmodel.mylistdetails.MyListDetailsListener
import com.elhady.movies.presentation.viewmodel.mylistdetails.MyListDetailsUiState


class  MyListDetailsAdapter(items: List<MyListDetailsUiState>, listener: MyListDetailsListener):
    BaseAdapter<MyListDetailsUiState>(items, listener) {

    override val layoutID = R.layout.item_my_list_details
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
