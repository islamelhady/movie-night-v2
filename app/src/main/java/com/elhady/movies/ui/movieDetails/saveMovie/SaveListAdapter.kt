package com.elhady.movies.ui.movieDetails.saveMovie

import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener


class SaveListAdapter(
    items: List<FavListItemUiState>, listener: SaveListInteractionListener
) : BaseAdapter<FavListItemUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_fav_list
}

interface SaveListInteractionListener : BaseInteractionListener {
    fun onClickSaveList(listId: Int)
}