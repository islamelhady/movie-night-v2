package com.elhady.movies.presentation.viewmodel.myrated

import com.elhady.movies.core.common.bases.BaseInteractionListener


interface MyRatedListner : BaseInteractionListener {
    fun onBackPressed()
    fun onClickMovieChip()
    fun onClickTvShowChip()
}
