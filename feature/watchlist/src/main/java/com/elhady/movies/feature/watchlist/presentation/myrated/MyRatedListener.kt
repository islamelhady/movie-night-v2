package com.elhady.movies.feature.watchlist.presentation.myrated

import com.elhady.movies.core.ui.base.BaseInteractionListener


interface MyRatedListener : BaseInteractionListener {
    fun onBackPressed()
    fun onClickMovieChip()
    fun onClickTvShowChip()
}
