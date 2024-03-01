package com.elhady.ui.movieDetails.saveMovie

import com.elhady.base.BaseAdapter
import com.elhady.base.BaseInteractionListener
import com.elhady.ui.R
import com.elhady.viewmodel.movieDetails.saveMovie.FavListItemUiState


class SaveListAdapter(
    items: List<FavListItemUiState>, listener: SaveListInteractionListener
) : BaseAdapter<FavListItemUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_fav_list
}

interface SaveListInteractionListener : BaseInteractionListener {
    fun onClickSaveList(listId: Int)
}