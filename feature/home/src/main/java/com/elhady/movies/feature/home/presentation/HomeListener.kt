package com.elhady.movies.feature.home.presentation

import com.elhady.movies.core.ui.bases.BaseInteractionListener
import com.elhady.movies.core.common.ShowMoreType

interface HomeListener : BaseInteractionListener {
    fun onClickMovieItem(movieId: Int)
    fun onClickTvShowItem(tvId: Int)
    fun onClickShowMore(showMoreType: ShowMoreType)
}
