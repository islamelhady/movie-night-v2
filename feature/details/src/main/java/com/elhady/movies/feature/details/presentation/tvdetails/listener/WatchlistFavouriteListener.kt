package com.elhady.movies.feature.details.presentation.tvdetails.listener

interface WatchlistFavouriteListener {
    fun onFavourite()
    fun onWatchlist()
    fun onDone()
    fun onChipClick(id: Int)
    fun onCreateList(name: String)
    fun onDismiss()
}