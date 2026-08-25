package com.elhady.movies.feature.watchlist.presentation.mylistdetails

import com.elhady.movies.core.ui.base.BaseInteractionListener

interface MyListDetailsAdapterListener: BaseInteractionListener {
    fun onClickItem( itemId: Int, mediaType: String )
}