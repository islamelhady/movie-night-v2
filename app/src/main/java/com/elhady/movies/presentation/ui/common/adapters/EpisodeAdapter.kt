package com.elhady.movies.presentation.ui.common.adapters

import com.elhady.movies.BR
import com.elhady.movies.R
import com.elhady.movies.core.common.bases.BaseAdapter
import com.elhady.movies.presentation.viewmodel.common.listener.EpisodeListener
import com.elhady.movies.presentation.viewmodel.common.model.EpisodeHorizontalUIState

class EpisodeAdapter(
    list: List<EpisodeHorizontalUIState>,
    listener: EpisodeListener
) : BaseAdapter<EpisodeHorizontalUIState>(list, listener) {
    override val layoutID = R.layout.item_episode_horizontal
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
