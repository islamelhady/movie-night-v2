package com.elhady.movies.presentation.viewmodel.tvshows

import com.elhady.movies.core.common.bases.BaseInteractionListener


interface TVShowsListener : BaseInteractionListener {
    fun onClickTVShowItem(tvId: Int)
    fun onClickOnTheAiringTVShowsResult()
    fun onClickAiringTodayTVShowsResult()
    fun onClickTopRatedTVShowsResult()
    fun onClickPopularTVShowsResult()
    fun onClickScrollToTopScreen()
}
