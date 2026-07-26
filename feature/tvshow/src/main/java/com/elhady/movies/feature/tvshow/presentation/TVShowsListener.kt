package com.elhady.movies.feature.tvshow.presentation

import com.elhady.movies.core.ui.bases.BaseInteractionListener


interface TVShowsListener : BaseInteractionListener {
    fun onClickTVShowItem(tvId: Int)
    fun onClickOnTheAiringTVShowsResult()
    fun onClickAiringTodayTVShowsResult()
    fun onClickTopRatedTVShowsResult()
    fun onClickPopularTVShowsResult()
    fun onClickScrollToTopScreen()
}
