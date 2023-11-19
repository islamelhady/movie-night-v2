package com.elhady.movies.ui.movieDetails

import com.elhady.movies.ui.base.BaseInteractionListener

interface DetailsInteractionListener: BaseInteractionListener {
    fun onClickBackButton()
    fun onClickPlayTrailer()
    fun onclickViewReviews()
    fun onClickFavourite()
}