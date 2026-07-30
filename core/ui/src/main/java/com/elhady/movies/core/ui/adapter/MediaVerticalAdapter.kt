package com.elhady.movies.core.ui.adapter

import com.elhady.movies.core.ui.BR
import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.core.ui.interaction.MediaListener
import com.elhady.movies.core.ui.state.MediaVerticalUiState

class MediaVerticalAdapter(
    list: List<MediaVerticalUiState>,
    listener: MediaListener
) : BaseAdapter<MediaVerticalUiState>(list, listener) {
    override val layoutID = com.elhady.movies.core.ui.R.layout.item_media_vertical
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
