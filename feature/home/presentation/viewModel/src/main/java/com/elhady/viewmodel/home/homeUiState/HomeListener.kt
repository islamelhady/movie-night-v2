package com.elhady.viewmodel.home.homeUiState

import com.elhady.base.BaseInteractionListener
import com.elhady.usecase.seeAllMedia.ShowMoreType


interface HomeListener: BaseInteractionListener {
    fun onClickMovieDetails(id: Int)
    fun onClickTVShowDetails(id: Int)
    fun onClickActorDetails(id: Int)
    fun onClickShowMore(type: ShowMoreType)
}