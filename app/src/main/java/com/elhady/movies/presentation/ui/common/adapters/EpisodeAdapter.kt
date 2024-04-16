package com.elhady.movies.presentation.ui.common.adapters

import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseAdapter
import com.elhady.movies.presentation.viewmodel.common.listener.EpisodeListener
import com.elhady.movies.presentation.viewmodel.common.model.EpisodeHorizontalUIState

class EpisodeAdapter(
    list: List<EpisodeHorizontalUIState>,
    listener: EpisodeListener
) : BaseAdapter<EpisodeHorizontalUIState>(list, listener) {
    override val layoutID = R.layout.item_episode_horizontal
}