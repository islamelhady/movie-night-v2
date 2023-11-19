package com.elhady.movies.ui.favorite

import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener

class CreatedListAdapter(
    items: List<CreatedListUiState>,
    listener: CreatedListInteractionListener
) :
    BaseAdapter<CreatedListUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_saved_list
}

interface CreatedListInteractionListener : BaseInteractionListener {
    fun onListClick(item: CreatedListUiState)
}