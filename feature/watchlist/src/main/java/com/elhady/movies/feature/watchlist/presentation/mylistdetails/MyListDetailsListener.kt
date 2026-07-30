package com.elhady.movies.feature.watchlist.presentation.mylistdetails

import com.elhady.movies.core.ui.base.BaseInteractionListener


interface MyListDetailsListener : BaseInteractionListener {
    fun onClickItem(itemId: Int , mediaType: String)

    fun onClickBackButton()
}
