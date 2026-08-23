package com.elhady.movies.feature.details.presentation.episodedetails

interface EpisodeDetailsListener {
    fun onClickBack()
    fun onClickRate()
    fun onClickPlayFullScreen(videoKey: String)
    fun onClickRetry()
}
