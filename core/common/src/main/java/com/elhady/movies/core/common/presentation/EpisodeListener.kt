package com.elhady.movies.core.common.presentation

import com.elhady.movies.core.common.bases.BaseInteractionListener

interface EpisodeListener: BaseInteractionListener {
    fun onClickEpisode(id: Int)
}
