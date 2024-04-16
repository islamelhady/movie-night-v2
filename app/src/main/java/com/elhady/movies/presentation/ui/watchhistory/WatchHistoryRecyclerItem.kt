package com.elhady.movies.presentation.ui.watchhistory

import com.elhady.movies.presentation.viewmodel.watchhistory.MovieUiState


sealed class WatchHistoryRecyclerItem {
    data class MovieCard(val movie: MovieUiState) : WatchHistoryRecyclerItem()
    data class Title(val title: String) : WatchHistoryRecyclerItem()
}
