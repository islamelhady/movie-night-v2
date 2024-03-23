package com.elhady.movies.presentation.viewmodel.common.listener

import com.elhady.movies.core.bases.BaseInteractionListener

interface EpisodeListener: BaseInteractionListener {
    fun onClickEpisode(id: Int)
}