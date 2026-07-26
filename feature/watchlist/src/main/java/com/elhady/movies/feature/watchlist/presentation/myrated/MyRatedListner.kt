package com.elhady.movies.feature.watchlist.presentation.myrated

import com.elhady.movies.core.ui.bases.BaseInteractionListener


interface MyRatedListner : BaseInteractionListener {
    fun onBackPressed()
    fun onClickMovieChip()
    fun onClickTvShowChip()
}
