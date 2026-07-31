package com.elhady.movies.feature.watchlist.presentation.watchhistory

import com.elhady.movies.feature.watchlist.presentation.watchhistory.MovieUiState


sealed class WatchHistoryRecyclerItem {
    data class MovieCard(val movie: MovieUiState) : WatchHistoryRecyclerItem()
    data class Title(val title: String) : WatchHistoryRecyclerItem()
}
