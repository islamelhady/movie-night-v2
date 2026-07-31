package com.elhady.movies.feature.tvshow.presentation.tvshow

import com.elhady.movies.core.ui.base.BaseInteractionListener


interface TvShowListener : BaseInteractionListener {
    fun onClickTvShowItem(tvId: Int)
    fun onClickOnTheAirTvShowsResult()
    fun onClickAiringTodayTvShowsResult()
    fun onClickTopRatedTvShowsResult()
    fun onClickPopularTvShowsResult()
    fun onClickScrollToTopScreen()
}
