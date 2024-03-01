package com.elhady.ui.profile.watchHistory

import com.elhady.base.BaseAdapter
import com.elhady.base.BaseInteractionListener
import com.elhady.ui.R
import com.elhady.viewmodel.profile.watchHistory.MediaHistoryUiState


class WatchHistoryAdapter(
    items: List<MediaHistoryUiState>,
    listener: WatchHistoryInteractionListener,
) : BaseAdapter<MediaHistoryUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_watch_history
}

interface WatchHistoryInteractionListener : BaseInteractionListener {
    fun onClickMedia(item: MediaHistoryUiState)
}