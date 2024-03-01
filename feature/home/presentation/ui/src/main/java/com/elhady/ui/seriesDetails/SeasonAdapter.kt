package com.elhady.ui.seriesDetails

import com.elhady.base.BaseInteractionListener
import com.elhady.ui.R
import com.elhady.viewmodel.seriesDetails.SeasonUiState


class SeasonAdapter(items: List<SeasonUiState>, listener: SeasonInteractionListener): BaseAdapter<SeasonUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_season
}

interface SeasonInteractionListener: BaseInteractionListener {
    fun onClickSeason(seasonNumber: Int, seasonName: String)
}