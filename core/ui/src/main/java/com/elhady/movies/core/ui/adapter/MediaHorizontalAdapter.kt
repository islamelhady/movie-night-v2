package com.elhady.movies.core.ui.adapter

import com.elhady.movies.core.ui.BR
import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.core.ui.interaction.MediaListener
import com.elhady.movies.core.ui.state.MediaHorizontalUiState

class MediaHorizontalAdapter(
    list: List<MediaHorizontalUiState>,
    listener: MediaListener
) : BaseAdapter<MediaHorizontalUiState>(list, listener) {
    override val layoutID = com.elhady.movies.core.ui.R.layout.item_media_horizontal
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
