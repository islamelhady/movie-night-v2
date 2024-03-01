package com.elhady.viewmodel.movieDetails

import com.elhady.base.BaseInteractionListener


interface DetailsInteractionListener: BaseInteractionListener {
    fun onClickBackButton()
    fun onClickPlayTrailer()
    fun onclickViewReviews()
    fun onClickFavourite()
}