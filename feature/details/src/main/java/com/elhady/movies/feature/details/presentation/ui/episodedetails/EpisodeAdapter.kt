package com.elhady.movies.feature.details.presentation.ui.episodedetails

import com.elhady.movies.feature.details.BR
import com.elhady.movies.feature.details.R
import com.elhady.movies.core.ui.bases.BaseAdapter
import com.elhady.movies.feature.details.presentation.episodedetails.EpisodeListener
import com.elhady.movies.feature.details.presentation.episodedetails.EpisodeHorizontalUIState

class EpisodeAdapter(
    list: List<EpisodeHorizontalUIState>,
    listener: EpisodeListener
) : BaseAdapter<EpisodeHorizontalUIState>(list, listener) {
    override val layoutID = R.layout.item_episode_horizontal
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
