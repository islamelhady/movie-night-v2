package com.elhady.movies.ui.seriesDetails.episodes

import com.elhady.movies.R
import com.elhady.movies.ui.base.BaseAdapter
import com.elhady.movies.ui.base.BaseInteractionListener

class EpisodeAdapter(items: List<SeasonEpisodesUiState>, listener: BaseInteractionListener): BaseAdapter<SeasonEpisodesUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_episode
}