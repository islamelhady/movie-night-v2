package com.elhady.ui.seriesDetails.episodes

import com.elhady.base.BaseAdapter
import com.elhady.base.BaseInteractionListener
import com.elhady.ui.R
import com.elhady.viewmodel.seriesDetails.episodes.SeasonEpisodesUiState


class EpisodeAdapter(items: List<SeasonEpisodesUiState>, listener: BaseInteractionListener): BaseAdapter<SeasonEpisodesUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_episode
}

interface EpisodesInteraction: BaseInteractionListener