package com.elhady.movies.ui.favorite.details

import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener

class ListDetailsAdapter(
    lists: List<FavMediaUiState>,
    listener: ListDetailsInteractionListener
) : BaseAdapter<FavMediaUiState>(lists, listener) {
    override val layoutID = R.layout.item_list_details
}

interface ListDetailsInteractionListener : BaseInteractionListener {
    fun onItemClick(item: FavMediaUiState)
}