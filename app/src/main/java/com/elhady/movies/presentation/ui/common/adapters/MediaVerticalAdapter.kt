package com.elhady.movies.presentation.ui.common.adapters

import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseAdapter
import com.elhady.movies.presentation.viewmodel.common.listener.MediaListener
import com.elhady.movies.presentation.viewmodel.common.model.MediaVerticalUIState

class MediaVerticalAdapter(
    list: List<MediaVerticalUIState>,
    listener: MediaListener
) : BaseAdapter<MediaVerticalUIState>(list, listener) {
    override val layoutID = R.layout.item_media_vertical
}