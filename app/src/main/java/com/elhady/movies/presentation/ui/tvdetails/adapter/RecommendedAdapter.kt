package com.elhady.movies.presentation.ui.tvdetails.adapter

import com.elhady.movies.BR
import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseAdapter
import com.elhady.movies.presentation.viewmodel.common.listener.MediaListener
import com.elhady.movies.presentation.viewmodel.common.model.MediaVerticalUIState


class RecommendedAdapter(
    itemRecommended: List<MediaVerticalUIState>,
    listener: MediaListener
) : BaseAdapter<MediaVerticalUIState>(itemRecommended, listener) {
    override val layoutID = R.layout.item_media_vertical
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}