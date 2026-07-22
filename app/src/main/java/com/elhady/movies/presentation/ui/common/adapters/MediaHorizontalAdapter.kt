package com.elhady.movies.presentation.ui.common.adapters

import com.elhady.movies.BR
import com.elhady.movies.R
import com.elhady.movies.core.common.bases.BaseAdapter
import com.elhady.movies.core.common.presentation.MediaListener
import com.elhady.movies.core.common.presentation.model.MediaHorizontalUIState

class MediaHorizontalAdapter(
    list: List<MediaHorizontalUIState>,
    listener: MediaListener
) : BaseAdapter<MediaHorizontalUIState>(list, listener) {
    override val layoutID = com.elhady.movies.core.ui.R.layout.item_media_horizontal
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
