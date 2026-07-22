package com.elhady.movies.feature.details.presentation.episodedetails

import com.elhady.movies.core.common.bases.BaseInteractionListener


interface EpisodeDetailsListener : BaseInteractionListener {
    fun clickToBack()
    fun clickToRate(episodeId: Int)
    fun clickToPlayFullScreen(videoKey: String)
}
