package com.elhady.movies.presentation.viewmodel.moviedetails

import com.elhady.movies.core.bases.BaseInteractionListener


interface MovieDetailsListener : BaseInteractionListener {
    fun onClickPlayTrailer()
    fun onClickRateMovie()
    fun onClickBackButton()
    fun onClickShowMore(movieId:Int)
}