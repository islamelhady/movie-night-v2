package com.elhady.ui.favorite

import com.elhady.base.BaseAdapter
import com.elhady.base.BaseInteractionListener
import com.elhady.ui.R
import com.elhady.viewmodel.favorite.CreatedListUiState


class CreatedListAdapter(
    items: List<CreatedListUiState>,
    listener: CreatedListInteractionListener
) :
    BaseAdapter<CreatedListUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_saved_list
}

interface CreatedListInteractionListener : BaseInteractionListener {
    fun onListClick(item: CreatedListUiState)
    fun onClickDeleteList(id: Int)
}