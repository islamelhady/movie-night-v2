package com.elhady.movies.feature.details.presentation.ui.tvdetails.adapter

import com.elhady.movies.feature.details.BR
import com.elhady.movies.core.ui.bases.BaseAdapter
import com.elhady.movies.core.ui.listener.MediaListener
import com.elhady.movies.core.ui.model.MediaVerticalUIState


class RecommendedAdapter(
    itemRecommended: List<MediaVerticalUIState>,
    listener: MediaListener
) : BaseAdapter<MediaVerticalUIState>(itemRecommended, listener) {
    override val layoutID = com.elhady.movies.core.ui.R.layout.item_media_vertical
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
