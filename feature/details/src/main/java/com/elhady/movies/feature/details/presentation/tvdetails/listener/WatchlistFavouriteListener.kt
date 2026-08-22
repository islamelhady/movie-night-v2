package com.elhady.movies.feature.details.presentation.tvdetails.listener

import com.elhady.movies.core.ui.interaction.ChipListener

interface WatchlistFavouriteListener : ChipListener {
    fun onFavourite()
    fun onWatchlist()
    fun onDone()
    override fun onChipClick(id: Int)
    fun onCreateList(name: String)
    fun onDismiss()
}
