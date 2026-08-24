package com.elhady.movies.feature.watchlist.presentation.mylist

import com.elhady.movies.core.ui.base.BaseInteractionListener

interface MyListAdapterListener : BaseInteractionListener {

    fun onClickItem(listId: Int, listType: String = "movie", listName: String = "favorite")
    fun onClickDelete(listId: Int, listName: String)
}