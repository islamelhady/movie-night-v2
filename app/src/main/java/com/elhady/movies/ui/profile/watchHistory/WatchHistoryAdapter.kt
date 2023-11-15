package com.elhady.movies.ui.profile.watchHistory

import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener


class WatchHistoryAdapter(
    items: List<MediaHistoryUiState>,
    listener: WatchHistoryInteractionListener,
) : BaseAdapter<MediaHistoryUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_watch_history
}

interface WatchHistoryInteractionListener : BaseInteractionListener {
    fun onClickMedia(item: MediaHistoryUiState)
}