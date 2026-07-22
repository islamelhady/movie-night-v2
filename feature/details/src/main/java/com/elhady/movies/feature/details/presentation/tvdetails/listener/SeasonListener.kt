package com.elhady.movies.feature.details.presentation.tvdetails.listener

import com.elhady.movies.core.common.bases.BaseInteractionListener


interface SeasonListener : BaseInteractionListener {
    fun onClickSeason(seasonNumber: Int)
}
