package com.elhady.movies.feature.watchlist.presentation.lists

import com.elhady.movies.core.common.MediaType
import com.elhady.movies.core.ui.base.BaseInteractionListener

interface ListsAdapterListener : BaseInteractionListener {

    fun onClickItem(listId: Int, listType: MediaType = MediaType.MOVIE, listName: String = "favorite")
    fun onClickDelete(listId: Int, listName: String)
}