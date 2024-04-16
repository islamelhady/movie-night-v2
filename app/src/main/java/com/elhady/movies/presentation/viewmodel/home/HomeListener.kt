package com.elhady.movies.presentation.viewmodel.home

import com.elhady.movies.core.bases.BaseInteractionListener
import com.elhady.movies.presentation.viewmodel.showmore.ShowMoreType


interface HomeListener : BaseInteractionListener {
    fun onClickMovieItem(movieId: Int)

    fun onClickTvShowItem(tvId: Int)

    fun onClickShowMore(showMoreType: ShowMoreType)
}