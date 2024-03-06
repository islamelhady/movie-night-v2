package com.elhady.viewmodel.home.homeUiState

import com.elhady.base.BaseInteractionListener
import com.elhady.viewmodel.home.ShowMoreType


interface HomeListener: BaseInteractionListener {
    fun onClickMovie(id: Int)
    fun onClickTVShow(id: Int)
    fun onClickActor(id: Int)
    fun onClickShowMore(type: ShowMoreType)
}