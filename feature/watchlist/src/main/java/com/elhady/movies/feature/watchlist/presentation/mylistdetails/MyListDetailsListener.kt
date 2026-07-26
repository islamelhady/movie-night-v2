package com.elhady.movies.feature.watchlist.presentation.mylistdetails

import com.elhady.movies.core.ui.bases.BaseInteractionListener


interface MyListDetailsListener : BaseInteractionListener {
    fun onClickItem(itemId: Int , mediaType: String)

    fun onClickBackButton()
}
