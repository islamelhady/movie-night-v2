package com.elhady.movies.feature.details.presentation.moviedetails

import com.elhady.movies.core.common.bases.BaseInteractionListener


interface MovieDetailsListener : BaseInteractionListener {
    fun onClickPlayTrailer()
    fun onClickRateMovie()
    fun onClickBackButton()
    fun onClickShowMore(movieId:Int)
}
