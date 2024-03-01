package com.elhady.ui.favorite.details

import com.elhady.base.BaseAdapter
import com.elhady.base.BaseInteractionListener
import com.elhady.ui.R
import com.elhady.viewmodel.favorite.details.FavMediaUiState


class ListDetailsAdapter(
    lists: List<FavMediaUiState>,
    listener: ListDetailsInteractionListener
) : BaseAdapter<FavMediaUiState>(lists, listener) {
    override val layoutID = R.layout.item_list_details
}

interface ListDetailsInteractionListener : BaseInteractionListener {
    fun onItemClick(item: FavMediaUiState)
}