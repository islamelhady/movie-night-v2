package com.elhady.movies.feature.details.presentation.ui.tvdetails.adapter

import com.elhady.movies.feature.details.BR
import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.core.ui.interaction.MediaListener
import com.elhady.movies.core.ui.state.MediaVerticalUiState


class RecommendedAdapter(
    itemRecommended: List<MediaVerticalUiState>,
    listener: MediaListener
) : BaseAdapter<MediaVerticalUiState>(itemRecommended, listener) {
    override val layoutID = com.elhady.movies.core.ui.R.layout.item_media_vertical
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
