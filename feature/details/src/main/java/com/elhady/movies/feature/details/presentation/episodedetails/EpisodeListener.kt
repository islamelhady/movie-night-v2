package com.elhady.movies.feature.details.presentation.episodedetails

import com.elhady.movies.core.ui.base.BaseInteractionListener

interface EpisodeListener: BaseInteractionListener {
    fun onClickEpisode(id: Int)
}