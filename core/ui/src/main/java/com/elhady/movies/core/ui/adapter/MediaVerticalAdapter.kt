package com.elhady.movies.core.ui.adapter

import com.elhady.movies.core.ui.BR
import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.core.ui.interaction.MediaListener
import com.elhady.movies.core.ui.state.MediaVerticalUIState

class MediaVerticalAdapter(
    list: List<MediaVerticalUIState>,
    listener: MediaListener
) : BaseAdapter<MediaVerticalUIState>(list, listener) {
    override val layoutID = com.elhady.movies.core.ui.R.layout.item_media_vertical
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
