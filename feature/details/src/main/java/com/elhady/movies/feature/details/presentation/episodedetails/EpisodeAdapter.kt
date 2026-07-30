package com.elhady.movies.feature.details.presentation.episodedetails

import com.elhady.movies.feature.details.BR
import com.elhady.movies.feature.details.R
import com.elhady.movies.core.ui.base.BaseAdapter

class EpisodeAdapter(
    list: List<EpisodeHorizontalUiState>,
    listener: EpisodeListener
) : BaseAdapter<EpisodeHorizontalUiState>(list, listener) {
    override val layoutID = R.layout.item_episode_horizontal
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
