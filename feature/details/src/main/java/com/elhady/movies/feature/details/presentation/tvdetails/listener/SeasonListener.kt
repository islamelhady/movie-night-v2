package com.elhady.movies.feature.details.presentation.tvdetails.listener

import com.elhady.movies.core.ui.bases.BaseInteractionListener


interface SeasonListener : BaseInteractionListener {
    fun onClickSeason(seasonNumber: Int)
}
