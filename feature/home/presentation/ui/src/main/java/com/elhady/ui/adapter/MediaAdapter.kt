package com.elhady.ui.adapter

import com.elhady.base.BaseAdapter
import com.elhady.base.BaseInteractionListener
import com.elhady.viewmodel.models.MediaUiState


class MediaAdapter(items: List<MediaUiState>, listener: MediaInteractionListener, layout: Int) :
    BaseAdapter<MediaUiState>(items, listener) {
    override val layoutID: Int = layout
}

interface MediaInteractionListener : BaseInteractionListener {
    fun onClickMedia(mediaId: Int)
}