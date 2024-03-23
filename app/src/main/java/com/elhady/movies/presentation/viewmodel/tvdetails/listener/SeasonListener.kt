package com.elhady.movies.presentation.viewmodel.tvdetails.listener

import com.elhady.movies.core.bases.BaseInteractionListener


interface SeasonListener : BaseInteractionListener {
    fun onClickSeason(seasonNumber: Int)
}