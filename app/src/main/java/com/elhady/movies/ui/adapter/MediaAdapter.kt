package com.elhady.movies.ui.adapter

import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener
import com.elhady.movies.ui.models.MediaUiState


class MediaAdapter(items: List<MediaUiState>,  listener: MediaInteractionListener, layout: Int) :
    BaseAdapter<MediaUiState>(items, listener) {
    override val layoutID: Int = layout
}

interface MediaInteractionListener : BaseInteractionListener {
    fun onClickMedia(mediaId: Int)
}