package com.elhady.movies.feature.watchlist.presentation.listcontents

import com.elhady.movies.core.ui.base.BaseInteractionListener

interface ListContentsAdapterListener: BaseInteractionListener {
    fun onClickItem( itemId: Int, mediaType: String )
}