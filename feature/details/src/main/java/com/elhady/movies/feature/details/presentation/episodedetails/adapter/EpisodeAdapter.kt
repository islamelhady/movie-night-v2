package com.elhady.movies.feature.details.presentation.episodedetails.adapter

import com.elhady.movies.core.ui.base.BaseAdapter
import com.elhady.movies.feature.details.BR
import com.elhady.movies.feature.details.R
import com.elhady.movies.feature.details.presentation.episodedetails.EpisodeHorizontalUiState
import com.elhady.movies.feature.details.presentation.episodedetails.EpisodeInteractionListener

class EpisodeAdapter(
    list: List<EpisodeHorizontalUiState>,
    listener: EpisodeInteractionListener
) : BaseAdapter<EpisodeHorizontalUiState>(list, listener) {
    override val layoutID: Int = R.layout.item_episode
    override val itemVariableId: Int = BR.item
    override val listenerVariableId: Int = BR.listener
}
