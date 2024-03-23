package com.elhady.movies.presentation.viewmodel.tv_shows

import com.elhady.movies.core.bases.BaseInteractionListener


interface TVShowsListener : BaseInteractionListener {
    fun onClickTVShowItem(tvId: Int)
    fun onClickOnTheAiringTVShowsResult()
    fun onClickAiringTodayTVShowsResult()
    fun onClickTopRatedTVShowsResult()
    fun onClickPopularTVShowsResult()
    fun onClickScrollToTopScreen()
}